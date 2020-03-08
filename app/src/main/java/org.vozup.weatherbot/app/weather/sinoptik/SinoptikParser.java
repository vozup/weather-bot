package org.vozup.weatherbot.app.weather.sinoptik;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.vozup.weatherbot.app.weather.BasicParser;
import org.vozup.weatherbot.app.weather.Utils;
import org.vozup.weatherbot.model.weather.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sinoptik weather parser
 */
public class SinoptikParser implements BasicParser {
    private String cityLink;

    //TODO create tests
    public SinoptikParser(String cityLink) {
        this.cityLink = cityLink;
    }

    /**
     * Parsing weather on few days
     *
     * @param countOfDays counting from the current
     * @return Weather model on few days
     */
    @Override
    public Weather parseOnDays(int countOfDays) {
        Weather weather = new Weather();

        for (int i = 1; i <= countOfDays; i++) {
            WeatherOnDay weatherOnDay = parse(LocalDate.now().plusDays(i));

            weather.addDay(weatherOnDay);
        }

        return weather;
    }

    /**
     * Parsing weather on tomorrow
     *
     * @return Weather model on tomorrow
     */
    @Override
    public Weather parseOnTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        WeatherOnDay tomorrowWeather = parse(tomorrow);

        return new Weather(Collections.singletonList(tomorrowWeather));
    }

    /**
     * Parse weather on one day
     *
     * @param date date of day
     * @return weather on one day
     */
    private WeatherOnDay parse(LocalDate date) {
        Utils.validateLink(cityLink);

        String formatedDate = DateTimeFormatter.ISO_LOCAL_DATE.format(date);
        WeatherOnDay weatherOnDay = new WeatherOnDay();
        weatherOnDay.setDate(date);

        Document doc;
        try {
            doc = Jsoup.connect(cityLink + "/" + formatedDate).get();
            Elements weatherDetails = doc.select(".weatherDetails");

            //Time of weatherOnDay
            Elements grayTime = weatherDetails.select("tbody").select(".gray")
                    .select(".time").select("td");
            DayName dayName = new DayName(grayTime.size());
            List<TimeOfDay> timeOfDayList = new ArrayList<>();

            for (Element el : grayTime) {
                TimeOfDay timeOfDay = new TimeOfDay();
                timeOfDay.setHour(el.text());
                timeOfDay.setName(dayName.dayName());
                timeOfDayList.add(timeOfDay);
            }
            //Temperature
            Elements temperature = weatherDetails.select("tbody .temperature").select("td");
            for (int i = 0; i < temperature.size(); i++) {
                String temperat = temperature.get(i).text();
                timeOfDayList.get(i).setTemperature(temperat);
            }
            //Preassure
            Elements preassure = weatherDetails.select("tbody .gray").not(".time").get(0).select("td");
            for (int i = 0; i < preassure.size(); i++) {
                int preass = Integer.valueOf(preassure.get(i).text());
                timeOfDayList.get(i).setPressure(preass);
            }
            //Wind
            Elements windEl = weatherDetails.select("tbody .gray").not(".time").get(1)
                    .select("td").select("div");
            for (int i = 0; i < windEl.size(); i++) {
                double windStrength = Double.valueOf(windEl.get(i).text());
                Wind wind = new Wind();
                wind.setStength(windStrength);

                timeOfDayList.get(i).setWind(wind);
            }

            //Rain in percent
            Elements rainEl = weatherDetails.select("tbody tr").get(7).select("td");
            for (int i = 0; i < rainEl.size(); i++) {
                double rain = Double.valueOf(rainEl.get(i).text());

                timeOfDayList.get(i).setRain(new Rain(rain, RainType.PERCENT));
            }
            //Sunrise sunset
            String sunrise = doc.select(".lSide").select(".infoDaylight").select("span").get(0).text();
            String sunset = doc.select(".lSide").select(".infoDaylight").select("span").get(1).text();
            DayLight dayLight = new DayLight(sunrise, sunset);

            weatherOnDay.setDayLight(dayLight);
            weatherOnDay.setTimeOfDayList(timeOfDayList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return weatherOnDay;
    }

    public String getCityLink() {
        return cityLink;
    }

    public void setCityLink(String cityLink) {
        this.cityLink = cityLink;
    }
}

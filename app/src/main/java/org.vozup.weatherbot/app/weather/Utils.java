package org.vozup.weatherbot.app.weather;

import org.vozup.weatherbot.model.weather.RainType;
import org.vozup.weatherbot.model.weather.TimeOfDay;
import org.vozup.weatherbot.model.weather.Weather;
import org.vozup.weatherbot.model.weather.WeatherOnDay;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //TODO update regex
    public static boolean validateLink(String link) {
        boolean isOk = link.startsWith("http");

        if (!isOk) {
            throw new IllegalArgumentException("Wrong link");
        }

        return true;
    }

    public static List<StringBuilder> weatherConverter(Weather weather) {
        List<StringBuilder> results = new ArrayList<>();

        for (WeatherOnDay onDay : weather.getWeatherOnDays()) {
            StringBuilder day = new StringBuilder();
            day.append("Date: ").append(onDay.getDate()).append("\n")
                    .append("Sunrise: ").append(onDay.getDayLight().getSunrise()).append("\n")
                    .append("Sunset: ").append(onDay.getDayLight().getSunset()).append("\n");

            for (TimeOfDay timeOfDay : onDay.getTimeOfDayList()) {
                day.append(timeOfDay.getName()).append(" ").append(timeOfDay.getHour()).append("\n")
                        .append("Temperature: ").append(timeOfDay.getTemperature()).append("\n");

                if (timeOfDay.getRain().getRainType().equals(RainType.MILLIMETERS)) {
                    day.append("Rain: ").append(timeOfDay.getRain().getStrength()).append("mm\n");
                }
                if (timeOfDay.getRain().getRainType().equals(RainType.PERCENT)) {
                    day.append("Rain: ").append(timeOfDay.getRain().getStrength()).append("%\n");
                }

                day.append("Wind: ").append(timeOfDay.getWind().getStength()).append("\n")
                        .append("Pressure: ").append(timeOfDay.getPressure()).append("\n");
            }

            results.add(day);
        }

        return results;
    }
}

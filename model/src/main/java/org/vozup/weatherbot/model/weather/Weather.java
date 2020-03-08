package org.vozup.weatherbot.model.weather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main weather class
 * Model of weather witch contain weather on few days
 */
public class Weather {
    private List<WeatherOnDay> weatherOnDays = new ArrayList<>();

    public Weather(List<WeatherOnDay> weatherOnDays) {
        this.weatherOnDays = weatherOnDays;
    }

    public Weather() {
    }

    public void addDay(WeatherOnDay weatherOnDay) {
        weatherOnDays.add(weatherOnDay);
    }

    public void removeDay(LocalDate date) {
        for (WeatherOnDay d : weatherOnDays) {
            if (d.getDate().equals(date)) {
                weatherOnDays.remove(d);
            }
        }
    }

    public List<WeatherOnDay> getWeatherOnDays() {
        return Collections.unmodifiableList(weatherOnDays);
    }

    public void setWeatherOnDays(List<WeatherOnDay> weatherOnDays) {
        this.weatherOnDays = weatherOnDays;
    }
}

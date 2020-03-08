package org.vozup.weatherbot.model.weather;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Model of weather on one day
 */
public class WeatherOnDay {
    private LocalDate date;
    private List<TimeOfDay> timeOfDayList;
    private DayLight dayLight;

    public WeatherOnDay(LocalDate date, List<TimeOfDay> timeOfDayList, DayLight dayLight) {
        this.date = date;
        this.timeOfDayList = timeOfDayList;
        this.dayLight = dayLight;
    }

    public WeatherOnDay() {
    }

    public void addTimeOfDay(TimeOfDay timeOfDay) {
        timeOfDayList.add(timeOfDay);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<TimeOfDay> getTimeOfDayList() {
        return Collections.unmodifiableList(timeOfDayList);
    }

    public void setTimeOfDayList(List<TimeOfDay> timeOfDayList) {
        this.timeOfDayList = timeOfDayList;
    }

    public DayLight getDayLight() {
        return dayLight;
    }

    public void setDayLight(DayLight dayLight) {
        this.dayLight = dayLight;
    }
}

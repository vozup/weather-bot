package org.vozup.weatherbot.model.weather;

/**
 * Model of time of day
 */
public class TimeOfDay {
    private String name;
    private String hour;
    private String temperature;
    private Wind wind;
    private Rain rain;
    private int pressure;

    public TimeOfDay() {
    }

    public TimeOfDay(String name, String hour,
                     String temperature, Wind wind, Rain rain, int pressure) {
        this.name = name;
        this.hour = hour;
        this.temperature = temperature;
        this.wind = wind;
        this.rain = rain;
        this.pressure = pressure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
}

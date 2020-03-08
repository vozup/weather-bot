package org.vozup.weatherbot.model.weather;

/**
 * Model of wind
 */
public class Wind {
    private double stength;
    private String direction;

    public Wind(double stength, String direction) {
        this.stength = stength;
        this.direction = direction;
    }

    public Wind() {
    }

    public double getStength() {
        return stength;
    }

    public void setStength(double stength) {
        this.stength = stength;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}

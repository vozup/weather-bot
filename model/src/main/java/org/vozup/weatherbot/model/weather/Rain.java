package org.vozup.weatherbot.model.weather;

/**
 * Model of rain
 */
public class Rain {
    private double strength;
    private RainType rainType;

    public Rain(double strength, RainType rainType) {
        this.strength = strength;
        this.rainType = rainType;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public RainType getRainType() {
        return rainType;
    }

    public void setRainType(RainType rainType) {
        this.rainType = rainType;
    }
}

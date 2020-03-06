package org.vozup.weatherbot.model.sites;

public class DistrictRegionPair {
    private String district;
    private String region;

    public DistrictRegionPair() {
    }

    public DistrictRegionPair(String district, String region) {
        this.district = district;
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return district + " " + region;
    }
}

package org.vozup.weatherbot.app.weather.gismeteo;

/**
 * Helping class where contain district< region and their link
 */
public class DistrictRegionUrl {
    private String district;
    private String region;
    private String url;

    public DistrictRegionUrl() {
    }

    public DistrictRegionUrl(String district, String region) {
        this.district = district;
        this.region = region;
    }

    public DistrictRegionUrl(String district, String region, String url) {
        this.district = district;
        this.region = region;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return district + " " + region;
    }
}

package org.vozup.weatherbot.model.sites.sinoptik;

/**
 * Helping class where contain region and their link
 */
public class RegionUrlPair {
    private String region;
    private String url;

    public RegionUrlPair() {
    }

    public RegionUrlPair(String region, String url) {
        this.region = region;
        this.url = url;
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
}

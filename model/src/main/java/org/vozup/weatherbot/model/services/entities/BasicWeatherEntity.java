package org.vozup.weatherbot.model.services.entities;

import org.vozup.weatherbot.model.services.Sites;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class BasicWeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CITY")
    private String city;
    @Column(name = "REGION")
    private String region;
    @Column(name = "DISTRICT")
    private String district;
    @Column(name = "FULL_LOCATION")
    private String fullLocation;
    @Column(name = "URL")
    private String url;
    @Column(name = "SITES")
    @Enumerated(EnumType.STRING)
    private Sites site;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public BasicWeatherEntity() {
    }

    public BasicWeatherEntity(String city, String region, String district, String fullLocation, String url,
                              Sites site, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.city = city;
        this.region = region;
        this.district = district;
        this.fullLocation = fullLocation;
        this.url = url;
        this.site = site;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getFullLocation() {
        return fullLocation;
    }

    public void setFullLocation(String fullLocation) {
        this.fullLocation = fullLocation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Sites getSite() {
        return site;
    }

    public void setSite(Sites site) {
        this.site = site;
    }
}

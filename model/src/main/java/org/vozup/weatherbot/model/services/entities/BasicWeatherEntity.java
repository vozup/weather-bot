package org.vozup.weatherbot.model.services.entities;

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
    @Column(name = "FULL_LOCATION")
    private String fullLocation;
    @Column(name = "URL")
    private String url;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    public BasicWeatherEntity(String city, String region, String fullLocation,
                              String url, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.city = city;
        this.region = region;
        this.fullLocation = fullLocation;
        this.url = url;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public BasicWeatherEntity() {
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
}

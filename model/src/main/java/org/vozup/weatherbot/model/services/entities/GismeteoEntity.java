package org.vozup.weatherbot.model.services.entities;

import org.vozup.weatherbot.model.services.Sites;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "GISMETEO")
@Entity
public class GismeteoEntity extends BasicWeatherEntity {
    public GismeteoEntity() {
        super();
    }

    public GismeteoEntity(String city, String region, String district, String fullLocation, String url,
                          Sites site, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(city, region, district, fullLocation, url, site, createdAt, modifiedAt);
    }
}

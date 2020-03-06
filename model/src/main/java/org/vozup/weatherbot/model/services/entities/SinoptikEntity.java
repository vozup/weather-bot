package org.vozup.weatherbot.model.services.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "SINOPTIK")
@Entity
public class SinoptikEntity extends BasicWeatherEntity {
    public SinoptikEntity() {
        super();
    }

    public SinoptikEntity(String city, String region, String district, String fullLocation, String url,
                          LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(city, region, district, fullLocation, url, createdAt, modifiedAt);
    }
}

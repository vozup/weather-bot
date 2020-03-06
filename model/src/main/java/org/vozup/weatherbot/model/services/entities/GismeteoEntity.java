package org.vozup.weatherbot.model.services.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "GISMETEO", schema = "schema")
@Entity
public class GismeteoEntity extends BasicWeatherEntity {
    public GismeteoEntity() {
        super();
    }

    public GismeteoEntity(String city, String region, String fullLocation, String url,
                          LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(city, region, fullLocation, url, createdAt, modifiedAt);
    }
}

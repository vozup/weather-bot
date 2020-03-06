package org.vozup.weatherbot.model.services.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vozup.weatherbot.model.services.entities.GismeteoEntity;

public interface GismeteoRepo extends JpaRepository<GismeteoEntity, Long> {
}

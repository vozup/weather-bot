package org.vozup.weatherbot.model.services.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vozup.weatherbot.model.services.entities.SinoptikEntity;

public interface SinoptikRepo extends JpaRepository<SinoptikEntity, Long> {
}

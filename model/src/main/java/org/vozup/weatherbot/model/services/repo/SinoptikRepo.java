package org.vozup.weatherbot.model.services.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vozup.weatherbot.model.services.entities.SinoptikEntity;

@Repository
public interface SinoptikRepo extends JpaRepository<SinoptikEntity, Long> {
}

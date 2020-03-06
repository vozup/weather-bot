package org.vozup.weatherbot.model.services.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vozup.weatherbot.model.services.entities.SinoptikEntity;

import java.util.List;

@Repository
public interface SinoptikRepo extends JpaRepository<SinoptikEntity, Long> {
    List<SinoptikEntity> findAllByDistrictEquals(String district);

    List<SinoptikEntity> findAllByRegionEquals(String region);
}

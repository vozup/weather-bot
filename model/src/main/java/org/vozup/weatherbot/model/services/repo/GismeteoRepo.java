package org.vozup.weatherbot.model.services.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vozup.weatherbot.model.services.entities.GismeteoEntity;

import java.util.List;

@Repository
public interface GismeteoRepo extends JpaRepository<GismeteoEntity, Long> {
    List<GismeteoEntity> findAllByDistrictEquals(String district);

    List<GismeteoEntity> findAllByRegionEquals(String region);

    List<GismeteoEntity> findAllByCityEquals(String city);
}

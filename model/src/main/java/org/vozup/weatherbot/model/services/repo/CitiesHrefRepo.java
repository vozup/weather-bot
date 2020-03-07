package org.vozup.weatherbot.model.services.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vozup.weatherbot.model.services.Sites;
import org.vozup.weatherbot.model.services.entities.CitiesHref;

import java.util.List;

@Repository
public interface CitiesHrefRepo extends JpaRepository<CitiesHref, Long> {
    List<CitiesHref> findAllByDistrictEquals(String district);

    List<CitiesHref> findAllByRegionEquals(String region);

    List<CitiesHref> findAllByCityEquals(String city);

    List<CitiesHref> findAllByDistrictEqualsAndSiteEquals(String district, Sites site);

    List<CitiesHref> findAllByRegionEqualsAndSiteEquals(String district, Sites site);

    List<CitiesHref> findAllByCityEqualsAndSiteEquals(String district, Sites site);
}

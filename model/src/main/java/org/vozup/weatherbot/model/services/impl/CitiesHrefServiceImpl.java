package org.vozup.weatherbot.model.services.impl;

import org.springframework.stereotype.Service;
import org.vozup.weatherbot.model.services.Sites;
import org.vozup.weatherbot.model.services.entities.CitiesHref;
import org.vozup.weatherbot.model.services.repo.CitiesHrefRepo;
import org.vozup.weatherbot.model.services.service.BasicSiteService;

import java.util.List;
import java.util.Objects;

@Service
public class CitiesHrefServiceImpl implements BasicSiteService<CitiesHref> {
    private final CitiesHrefRepo citiesHrefRepo;

    public CitiesHrefServiceImpl(CitiesHrefRepo citiesHrefRepo) {
        this.citiesHrefRepo = citiesHrefRepo;
    }

    @Override
    public void save(CitiesHref entity) {
        Objects.requireNonNull(entity, "Entity is null");

        citiesHrefRepo.save(entity);
    }

    @Override
    public CitiesHref get(Long id) {
        return citiesHrefRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with id " + id + " cant find"));
    }

    @Override
    public List<CitiesHref> findAll() {
        return citiesHrefRepo.findAll();
    }

    @Override
    public List<CitiesHref> findAllByRegion(String region) {
        return citiesHrefRepo.findAllByRegionEquals(region);
    }

    @Override
    public List<CitiesHref> findAllByDistrict(String district) {
        return citiesHrefRepo.findAllByDistrictEquals(district);
    }

    @Override
    public List<CitiesHref> findByCity(String city) {
        return citiesHrefRepo.findAllByCityEquals(city);
    }

    @Override
    public List<CitiesHref> findAllByRegion(String region, Sites site) {
        return citiesHrefRepo.findAllByRegionEqualsAndSiteEquals(region, site);
    }

    @Override
    public List<CitiesHref> findAllByDistrict(String district, Sites site) {
        return citiesHrefRepo.findAllByDistrictEqualsAndSiteEquals(district, site);
    }

    @Override
    public List<CitiesHref> findByCity(String city, Sites site) {
        return citiesHrefRepo.findAllByCityEqualsAndSiteEquals(city, site);
    }
}

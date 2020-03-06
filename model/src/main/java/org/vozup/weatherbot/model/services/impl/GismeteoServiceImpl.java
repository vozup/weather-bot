package org.vozup.weatherbot.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vozup.weatherbot.model.services.entities.GismeteoEntity;
import org.vozup.weatherbot.model.services.repo.GismeteoRepo;
import org.vozup.weatherbot.model.services.service.BasicSiteService;

import java.util.List;
import java.util.Objects;

@Service
public class GismeteoServiceImpl implements BasicSiteService<GismeteoEntity> {
    private final GismeteoRepo gismeteoRepo;

    @Autowired
    public GismeteoServiceImpl(GismeteoRepo gismeteoRepo) {
        this.gismeteoRepo = gismeteoRepo;
    }

    @Override
    public void save(GismeteoEntity gismeteoEntity) {
        Objects.requireNonNull(gismeteoEntity);

        gismeteoRepo.save(gismeteoEntity);
    }

    @Override
    public GismeteoEntity get(Long id) {
        return gismeteoRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Cant find entity with id: " + id));
    }

    @Override
    public List<GismeteoEntity> findAll() {
        return gismeteoRepo.findAll();
    }

    @Override
    public List<GismeteoEntity> findAllByRegion(String region) {
        return gismeteoRepo.findAllByRegionEquals(region);
    }

    @Override
    public List<GismeteoEntity> findAllByDistrict(String district) {
        return gismeteoRepo.findAllByDistrictEquals(district);
    }

    @Override
    public List<GismeteoEntity> findByCity(String city) {
        return gismeteoRepo.findAllByCityEquals(city);
    }

    public GismeteoRepo getGismeteoRepo() {
        return gismeteoRepo;
    }
}

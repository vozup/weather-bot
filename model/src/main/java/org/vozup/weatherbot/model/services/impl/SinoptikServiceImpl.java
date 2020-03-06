package org.vozup.weatherbot.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vozup.weatherbot.model.services.entities.SinoptikEntity;
import org.vozup.weatherbot.model.services.repo.SinoptikRepo;
import org.vozup.weatherbot.model.services.service.BasicSiteService;

import java.util.List;
import java.util.Objects;

@Service
public class SinoptikServiceImpl implements BasicSiteService<SinoptikEntity> {
    private final SinoptikRepo sinoptikRepo;

    @Autowired
    public SinoptikServiceImpl(SinoptikRepo sinoptikRepo) {
        this.sinoptikRepo = sinoptikRepo;
    }

    @Override
    public void save(SinoptikEntity sinoptikEntity) {
        Objects.requireNonNull(sinoptikEntity);

        sinoptikRepo.save(sinoptikEntity);
    }

    @Override
    public SinoptikEntity get(Long id) {
        return sinoptikRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Cant find entity with id: " + id));
    }

    @Override
    public List<SinoptikEntity> findAll() {
        return sinoptikRepo.findAll();
    }

    @Override
    public List<SinoptikEntity> findAllByRegion(String region) {
        return sinoptikRepo.findAllByRegionEquals(region);
    }

    @Override
    public List<SinoptikEntity> findAllByDistrict(String district) {
        return sinoptikRepo.findAllByDistrictEquals(district);
    }

    public SinoptikRepo getSinoptikRepo() {
        return sinoptikRepo;
    }
}

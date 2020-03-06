package org.vozup.weatherbot.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vozup.weatherbot.model.services.repo.GismeteoRepo;
import org.vozup.weatherbot.model.services.service.GismeteoService;

@Service
public class GismeteoServiceImpl implements GismeteoService {
    private final GismeteoRepo gismeteoRepo;

    @Autowired
    public GismeteoServiceImpl(GismeteoRepo gismeteoRepo) {
        this.gismeteoRepo = gismeteoRepo;
    }

    public GismeteoRepo getGismeteoRepo() {
        return gismeteoRepo;
    }
}

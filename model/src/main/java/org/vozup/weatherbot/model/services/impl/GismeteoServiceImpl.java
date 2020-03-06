package org.vozup.weatherbot.model.services.impl;

import org.vozup.weatherbot.model.services.repo.GismeteoRepo;
import org.vozup.weatherbot.model.services.service.GismeteoService;


public class GismeteoServiceImpl implements GismeteoService {
    private final GismeteoRepo gismeteoRepo;

    public GismeteoServiceImpl(GismeteoRepo gismeteoRepo) {
        this.gismeteoRepo = gismeteoRepo;
    }

    public GismeteoRepo getGismeteoRepo() {
        return gismeteoRepo;
    }
}

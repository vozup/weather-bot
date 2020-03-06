package org.vozup.weatherbot.model.services.impl;

import org.vozup.weatherbot.model.services.repo.SinoptikRepo;
import org.vozup.weatherbot.model.services.service.SinoptikService;

public class SinoptikServiceImpl implements SinoptikService {
    private final SinoptikRepo sinoptikRepo;

    public SinoptikServiceImpl(SinoptikRepo sinoptikRepo) {
        this.sinoptikRepo = sinoptikRepo;
    }

    public SinoptikRepo getSinoptikRepo() {
        return sinoptikRepo;
    }
}

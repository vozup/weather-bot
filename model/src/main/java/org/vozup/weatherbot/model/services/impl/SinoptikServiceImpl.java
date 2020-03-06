package org.vozup.weatherbot.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vozup.weatherbot.model.services.repo.SinoptikRepo;
import org.vozup.weatherbot.model.services.service.SinoptikService;

@Service
public class SinoptikServiceImpl implements SinoptikService {
    private final SinoptikRepo sinoptikRepo;

    @Autowired
    public SinoptikServiceImpl(SinoptikRepo sinoptikRepo) {
        this.sinoptikRepo = sinoptikRepo;
    }

    public SinoptikRepo getSinoptikRepo() {
        return sinoptikRepo;
    }
}

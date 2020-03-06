package org.vozup.weatherbot.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vozup.weatherbot.model.services.impl.GismeteoServiceImpl;
import org.vozup.weatherbot.model.services.impl.SinoptikServiceImpl;
import org.vozup.weatherbot.model.services.repo.GismeteoRepo;
import org.vozup.weatherbot.model.services.repo.SinoptikRepo;
import org.vozup.weatherbot.model.services.service.GismeteoService;
import org.vozup.weatherbot.model.services.service.SinoptikService;

@Configuration
public class AppConfig {
    @Configuration
    public class ServiceConfig {
        @Bean
        public GismeteoService gismeteoService(GismeteoRepo gismeteoRepo) {
            return new GismeteoServiceImpl(gismeteoRepo);
        }

        @Bean
        public SinoptikService sinoptikService(SinoptikRepo sinoptikRepo) {
            return new SinoptikServiceImpl(sinoptikRepo);
        }
    }

}

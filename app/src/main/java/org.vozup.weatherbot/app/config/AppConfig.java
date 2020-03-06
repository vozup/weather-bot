package org.vozup.weatherbot.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.vozup.weatherbot.app.sites.gismeteo.GismeteoCities;
import org.vozup.weatherbot.app.sites.sinoptik.SinoptikCities;
import org.vozup.weatherbot.model.services.entities.GismeteoEntity;
import org.vozup.weatherbot.model.services.entities.SinoptikEntity;
import org.vozup.weatherbot.model.services.impl.GismeteoServiceImpl;
import org.vozup.weatherbot.model.services.impl.SinoptikServiceImpl;
import org.vozup.weatherbot.model.services.repo.GismeteoRepo;
import org.vozup.weatherbot.model.services.repo.SinoptikRepo;
import org.vozup.weatherbot.model.services.service.BasicSiteService;

@Configuration
public class AppConfig {
    @Configuration
    public class ServiceConfig {
        @Bean
        public BasicSiteService<GismeteoEntity> gismeteoService(GismeteoRepo gismeteoRepo) {
            return new GismeteoServiceImpl(gismeteoRepo);
        }

        @Bean
        public BasicSiteService<SinoptikEntity> sinoptikService(SinoptikRepo sinoptikRepo) {
            return new SinoptikServiceImpl(sinoptikRepo);
        }
    }

    @Configuration
    public class WeatherFromSites {
        @Bean
        public GismeteoCities gismeteoCities(BasicSiteService<GismeteoEntity> gismeteoService) {
            return new GismeteoCities(gismeteoService);
        }

        @Bean
        @Lazy
        public SinoptikCities sinoptikCities(BasicSiteService<SinoptikEntity> sinoptikService) {
            return new SinoptikCities(sinoptikService);
        }
    }

}

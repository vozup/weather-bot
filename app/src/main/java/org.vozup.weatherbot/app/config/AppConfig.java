package org.vozup.weatherbot.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.vozup.weatherbot.model.services.entities.GismeteoEntity;
import org.vozup.weatherbot.model.services.entities.SinoptikEntity;
import org.vozup.weatherbot.model.services.impl.GismeteoServiceImpl;
import org.vozup.weatherbot.model.services.impl.SinoptikServiceImpl;
import org.vozup.weatherbot.model.services.repo.GismeteoRepo;
import org.vozup.weatherbot.model.services.repo.SinoptikRepo;
import org.vozup.weatherbot.model.services.service.BasicSiteService;
import org.vozup.weatherbot.model.sites.BasicCities;
import org.vozup.weatherbot.model.sites.gismeteo.GismeteoCities;
import org.vozup.weatherbot.model.sites.sinoptik.SinoptikCities;

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
    @PropertySource(value = "customer.properties")
    public class WeatherFromSites {
        @Value("${thread.count:6}")
        private int threadCount;

        @Bean
        @Lazy
        public BasicCities gismeteoCities(BasicSiteService<GismeteoEntity> gismeteoService) {
            return new GismeteoCities(gismeteoService, threadCount);
        }

        @Bean
        @Lazy
        public BasicCities sinoptikCities(BasicSiteService<SinoptikEntity> sinoptikService) {
            return new SinoptikCities(sinoptikService, threadCount);
        }
    }

}

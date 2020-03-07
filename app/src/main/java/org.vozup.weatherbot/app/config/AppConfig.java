package org.vozup.weatherbot.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.vozup.weatherbot.model.services.entities.CitiesHref;
import org.vozup.weatherbot.model.services.impl.CitiesHrefServiceImpl;
import org.vozup.weatherbot.model.services.repo.CitiesHrefRepo;
import org.vozup.weatherbot.model.services.service.BasicSiteService;
import org.vozup.weatherbot.model.sites.BasicCities;
import org.vozup.weatherbot.model.sites.gismeteo.GismeteoCities;
import org.vozup.weatherbot.model.sites.sinoptik.SinoptikCities;
import org.vozup.weatherbot.telegram.WeatherSceduleBot;

@Configuration
public class AppConfig {
    @Configuration
    public class ServiceConfig {
        @Bean
        public BasicSiteService<CitiesHref> citiesHrefService(CitiesHrefRepo citiesHrefRepo) {
            return new CitiesHrefServiceImpl(citiesHrefRepo);
        }
    }

    @Configuration
    @PropertySource(value = "customer.properties")
    public class WeatherFromSites {
        @Value("${thread.count:6}")
        private int threadCount;

        @Bean
        @Lazy
        public BasicCities gismeteoCities(BasicSiteService<CitiesHref> siteService) {
            return new GismeteoCities(siteService, threadCount);
        }

        @Bean
        @Lazy
        public BasicCities sinoptikCities(BasicSiteService<CitiesHref> siteService) {
            return new SinoptikCities(siteService, threadCount);
        }
    }

    @Configuration
    @PropertySource("bot-settings.properties")
    public class TelegramApi {
        @Value("${bot.token:default}")
        private String botToken;
        @Value("${bot.name:default}")
        private String botName;

        /**
         * Register telegram bot
         * add bot-settings.properties file to resources dir
         * and add to them fields bot.token and bot.name
         * for correct registration
         *
         * @param service
         * @return
         */
        @Bean
        public BotSession initTelegramBot(BasicSiteService<CitiesHref> service) {
            if (botName.equals("default") || botToken.equals("default")) {
                System.err.println("Please add bot-settings.properties file to resources dir");
                System.err.println("And add to them fields bot.token and bot.name");
                throw new IllegalArgumentException("Telegram botToken and botName is default");
            }

            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();

            try {
                WeatherSceduleBot bot = new WeatherSceduleBot(service);
                bot.setBotName(botName);
                bot.setToken(botToken);

                return botsApi.registerBot(bot);
            } catch (TelegramApiException e) {
                System.err.println("Telegram bot register error");
                e.printStackTrace();
            }

            System.err.println("Telegram bot register error");
            return null;
        }
    }

}

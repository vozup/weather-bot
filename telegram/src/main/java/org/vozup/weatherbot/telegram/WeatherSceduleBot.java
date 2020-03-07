package org.vozup.weatherbot.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.vozup.weatherbot.model.services.entities.BasicWeatherEntity;
import org.vozup.weatherbot.model.services.service.BasicSiteService;

import java.util.List;

public class WeatherSceduleBot extends TelegramLongPollingBot {
    private List<BasicSiteService> services;
    private String token;
    private String botName;

    public WeatherSceduleBot(List<BasicSiteService> services) {
        super();
        this.services = services;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            StringBuilder result = new StringBuilder();

            for (BasicSiteService siteService : services) {
                List<? extends BasicWeatherEntity> cities = siteService.findByCity(update.getMessage().getText());

                for (BasicWeatherEntity city : cities) {
                    result.append(city.getCity()).append(" ")
                            .append(city.getDistrict()).append(" ")
                            .append(city.getRegion()).append("\n");
                }
            }

            if (result.length() == 0) {
                result.append("Такого города не найдено");
            }

            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(result.toString());
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }
}

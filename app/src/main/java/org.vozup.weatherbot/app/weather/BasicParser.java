package org.vozup.weatherbot.app.weather;

import org.vozup.weatherbot.model.weather.Weather;

public interface BasicParser {
    Weather parseOnDays(int countOfDays);

    Weather parseOnTomorrow();
}

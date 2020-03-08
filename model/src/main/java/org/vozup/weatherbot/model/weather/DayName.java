package org.vozup.weatherbot.model.weather;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Helping class for parser with names of time of day
 */
public class DayName {
    private Queue<String> timeOfDayNames = new LinkedList<>();

    public DayName(int count) {
        if (count == 4) {
            timeOfDayNames.add("ночь");
            timeOfDayNames.add("утро");
            timeOfDayNames.add("день");
            timeOfDayNames.add("вечер");
        } else if (count == 8) {
            timeOfDayNames.add("ночь");
            timeOfDayNames.add("ночь");
            timeOfDayNames.add("утро");
            timeOfDayNames.add("утро");
            timeOfDayNames.add("день");
            timeOfDayNames.add("день");
            timeOfDayNames.add("вечер");
            timeOfDayNames.add("вечер");
        } else {
            throw new IllegalArgumentException("Count of time of days name can be 4 or 8");
        }
    }

    public String dayName() {
        return timeOfDayNames.poll();
    }
}

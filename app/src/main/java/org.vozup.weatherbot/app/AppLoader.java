package org.vozup.weatherbot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "org.vozup.weatherbot.model",
        "org.vozup.weatherbot.app"
})
public class AppLoader {
    public static void main(String... args) {
        SpringApplication.run(AppLoader.class, args);
    }
}

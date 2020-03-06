package org.vozup.weatherbot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "org.vozup.weatherbot.app",
        "org.vozup.weatherbot.rest"
})
@EnableJpaRepositories(basePackages = "org.vozup.weatherbot.model.services.repo")
@EntityScan(basePackages = "org.vozup.weatherbot.model.services.entities")
public class AppLoader {
    public static void main(String... args) {
        SpringApplication.run(AppLoader.class, args);
    }
}

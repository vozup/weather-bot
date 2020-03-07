package org.vozup.weatherbot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.vozup.weatherbot.model.sites.BasicCities;

import java.util.List;

/**
 * Main rest controller
 */
@RestController
public class MainRestController {
    private List<BasicCities> citiesList;

    @GetMapping("/update-db")
    public ModelAndView updatingAllDb() {
        for (BasicCities city : citiesList) {
            city.fillDb();
        }

        return new ModelAndView("Wait");
    }

    @Autowired
    public void setCitiesList(List<BasicCities> citiesList) {
        this.citiesList = citiesList;
    }
}

package org.vozup.weatherbot.model.sites.sinoptik;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.vozup.weatherbot.model.services.entities.SinoptikEntity;
import org.vozup.weatherbot.model.services.service.BasicSiteService;
import org.vozup.weatherbot.model.sites.BasicCities;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Класс для парсинга всех городов и url которые им соответствуют
 * с сайта sinoptik.ua
 */
public class SinoptikCities implements BasicCities {
    private Logger log = Logger.getLogger(this.getClass().getName());
    private String slash = "/";

    private HashMap<String, String> hrefAndRegion;
    private final BasicSiteService<SinoptikEntity> sinoptikService;

    public SinoptikCities(BasicSiteService<SinoptikEntity> sinoptikService) {
        hrefAndRegion = new HashMap<>();
        this.sinoptikService = sinoptikService;
    }

    @Override
    public void fillDb() {
        log.info("Start filling DB Sinoptik");
        initAllCities(sinoptikService);
    }

    /**
     * Получить области и соответствующие url
     */
    private void initHrefAndRegion() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://sinoptik.ua/украина").get();

            Elements allRegions = doc.getElementsMatchingOwnText("Области Украины").next();

            for (Element el : allRegions.select("ul li a")) {
                hrefAndRegion.put(
                        el.text(),
                        "https:" + el.attr("href"));
            }

            //hrefAndRegion.forEach((k, v) -> System.out.println("Key: " + k + " Value: " + v));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получить города и соответствующие url
     */
    //FIXME Update regex
    private void initAllCities(BasicSiteService<SinoptikEntity> sinoptikService) {
        initHrefAndRegion();

        hrefAndRegion.forEach((region, url) -> {
            for (char ch = 'А'; ch < 'Я'; ch++) {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url + slash + ch).get();
                    Elements col4Cities = doc.select(".col4");

                    for (Element el : col4Cities.select("li")) {
                        SinoptikEntity sinoptikEntity = new SinoptikEntity();
                        String city = el.select("a").text();
                        city = city.replaceAll("^[а-я]+\\s", "");
                        sinoptikEntity.setCity(city);
                        String district = el.select("span").text();
                        sinoptikEntity.setRegion(region);
                        sinoptikEntity.setDistrict(district);
                        sinoptikEntity.setFullLocation(city + " " + district + " " + region);
                        sinoptikEntity.setUrl("https:" + el.select("a").attr("href"));
                        sinoptikEntity.setCreatedAt(LocalDateTime.now());
                        sinoptikService.save(sinoptikEntity);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BasicSiteService<SinoptikEntity> getSinoptikService() {
        return sinoptikService;
    }
}
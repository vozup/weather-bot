package org.vozup.weatherbot.model.sites.sinoptik;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.vozup.weatherbot.model.services.Sites;
import org.vozup.weatherbot.model.services.entities.SinoptikEntity;
import org.vozup.weatherbot.model.services.service.BasicSiteService;
import org.vozup.weatherbot.model.sites.BasicCities;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Класс для парсинга всех городов и url которые им соответствуют
 * с сайта sinoptik.ua
 */
public class SinoptikCities implements BasicCities {
    private Logger log = Logger.getLogger(this.getClass().getName());
    private String slash = "/";

    private List<RegionUrlPair> hrefAndRegion;
    private final BasicSiteService<SinoptikEntity> sinoptikService;
    private int threadCount;

    public SinoptikCities(BasicSiteService<SinoptikEntity> sinoptikService, int threadCount) {
        hrefAndRegion = new ArrayList<>();
        this.sinoptikService = sinoptikService;

        if (threadCount <= 0) {
            this.threadCount = 6;
        } else {
            this.threadCount = threadCount;
        }
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
                hrefAndRegion.add(new RegionUrlPair(el.text(), "https:" + el.attr("href")));
            }

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
        multiThreadInit(hrefAndRegion);
    }

    private void multiThreadInit(final List<RegionUrlPair> hrefAndRegion) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Runnable> tasks = new ArrayList<>();

        int elementCount = hrefAndRegion.size();
        int start;
        int end = 0;
        int fullPart = (int) Math.floor(elementCount / threadCount);

        for (int i = 0; i < threadCount; i++) {
            start = end;
            if (i == threadCount - 1) {
                end = elementCount - 1;
            } else {
                end = fullPart + start;
            }

            List<RegionUrlPair> part = hrefAndRegion.subList(start, end);
            tasks.add(() -> {
                log.info(this.getClass().getName() + " " + Thread.currentThread().getName() + " start");
                for (RegionUrlPair pair : part) {
                    for (char ch = 'А'; ch < 'Я'; ch++) {
                        Document doc = null;
                        try {
                            doc = Jsoup.connect(pair.getUrl() + slash + ch).get();
                            Elements col4Cities = doc.select(".col4");

                            for (Element el : col4Cities.select("li")) {
                                SinoptikEntity sinoptikEntity = new SinoptikEntity();
                                String city = el.select("a").text();
                                city = city.replaceAll("^[а-я]+\\s", "");
                                sinoptikEntity.setCity(city);
                                String district = el.select("span").text();
                                sinoptikEntity.setRegion(pair.getRegion());
                                sinoptikEntity.setDistrict(district);
                                sinoptikEntity.setFullLocation(city + " " + district + " " + pair.getRegion());
                                sinoptikEntity.setUrl("https:" + el.select("a").attr("href"));
                                sinoptikEntity.setCreatedAt(LocalDateTime.now());
                                sinoptikEntity.setSite(Sites.SINOPTIK);
                                sinoptikService.save(sinoptikEntity);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                log.info(this.getClass().getName() + " " + Thread.currentThread().getName() + " end");
            });
        }

        for (Runnable r : tasks) {
            executorService.submit(r);
        }

        executorService.shutdown();
    }

    public BasicSiteService<SinoptikEntity> getSinoptikService() {
        return sinoptikService;
    }
}
package org.vozup.weatherbot.model.sites.sinoptik;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.vozup.weatherbot.model.services.Sites;
import org.vozup.weatherbot.model.services.entities.CitiesHref;
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
 * Class where we parsing cities
 * and their links from sinoptik and save them in db
 */
public class SinoptikCities implements BasicCities {
    private Logger log = Logger.getLogger(this.getClass().getName());
    private String slash = "/";

    private List<RegionUrlPair> hrefAndRegion;
    private final BasicSiteService<CitiesHref> siteService;
    private int threadCount;

    public SinoptikCities(BasicSiteService<CitiesHref> siteService, int threadCount) {
        hrefAndRegion = new ArrayList<>();
        this.siteService = siteService;

        if (threadCount <= 0) {
            this.threadCount = 6;
        } else {
            this.threadCount = threadCount;
        }
    }

    @Override
    public void fillDb() {
        log.info("Start filling DB Sinoptik");
        initAllCities();
    }

    /**
     * Give regions and their links
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

    //FIXME Update regex
    private void initAllCities() {
        initHrefAndRegion();
        multiThreadInit(hrefAndRegion);
    }

    /**
     * Multi thread filling DB
     *
     * @param hrefAndRegion list with info of regions and their links
     */
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
                        Document doc;
                        try {
                            doc = Jsoup.connect(pair.getUrl() + slash + ch).get();
                            Elements col4Cities = doc.select(".col4");

                            for (Element el : col4Cities.select("li")) {
                                CitiesHref citiesHref = new CitiesHref();
                                String city = el.select("a").text();
                                city = city.replaceAll("^[а-я]+\\s", "");
                                citiesHref.setCity(city);
                                String district = el.select("span").text();
                                citiesHref.setRegion(pair.getRegion());
                                citiesHref.setDistrict(district);
                                citiesHref.setFullLocation(city + " " + district + " " + pair.getRegion());
                                citiesHref.setUrl("https:" + el.select("a").attr("href"));
                                citiesHref.setCreatedAt(LocalDateTime.now());
                                citiesHref.setSite(Sites.SINOPTIK);
                                siteService.save(citiesHref);
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
}
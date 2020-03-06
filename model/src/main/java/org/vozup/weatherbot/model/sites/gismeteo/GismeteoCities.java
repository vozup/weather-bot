package org.vozup.weatherbot.model.sites.gismeteo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.vozup.weatherbot.model.services.entities.GismeteoEntity;
import org.vozup.weatherbot.model.services.service.BasicSiteService;
import org.vozup.weatherbot.model.sites.BasicCities;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class GismeteoCities implements BasicCities {
    private static Logger log = Logger.getLogger(GismeteoCities.class.getName());

    private HashMap<String, String> hrefAndRegion;
    private List<DistrictRegionUrl> hrefAndRegionExt;
    private final BasicSiteService<GismeteoEntity> gismeteoService;
    private int threadCount;

    public GismeteoCities(BasicSiteService<GismeteoEntity> gismeteoService, int threadCount) {
        hrefAndRegion = new HashMap<>();
        hrefAndRegionExt = new ArrayList<>();
        this.gismeteoService = gismeteoService;

        if (threadCount <= 0) {
            this.threadCount = 6;
        } else {
            this.threadCount = threadCount;
        }
    }

    @Override
    public void fillDb() {
        log.info("Start filling DB Gismeteo");
        initAllCities(gismeteoService);
    }

    /**
     * Получить области и соответствующие url
     */
    private void initHrefAndRegion() {
        Document doc;
        try {
            doc = Jsoup.connect("https://www.gismeteo.ua/catalog/ukraine/").get();

            //First of all we need get map Region and ther links
            Elements allRegions = doc.getElementsByClass("catalog_sides")
                    .select(".catalog_side")
                    .get(0)
                    .select(".catalog_block .catalog_list");

            for (Element el : allRegions.select(".catalog_item a")) {
                //Regions and links
                hrefAndRegion.put(
                        el.text(),
                        "https://www.gismeteo.ua" + el.attr("href"));
            }
            //Then we walk all region and creates map with region name, district name and district links
            hrefAndRegion.forEach((region, url) -> {
                Document docExt;
                try {
                    docExt = Jsoup.connect(url).get();
                    Elements citiesInRegion = docExt.getElementsByClass("catalog_sides")
                            .select(".catalog_side");

                    if (citiesInRegion.isEmpty()) {
                        citiesInRegion = docExt.getElementsByClass("catalog_sides")
                                .select(".catalog_block .catalog_list a");
                    } else {
                        citiesInRegion = citiesInRegion
                                .get(0)
                                .select(".catalog_block .catalog_list a");
                    }

                    for (Element el : citiesInRegion) {
                        String district = el.text();
                        hrefAndRegionExt.add(new DistrictRegionUrl(district,
                                region,
                                "https://www.gismeteo.ua" + el.attr("href")));
                    }
                } catch (IOException e) {
                    log.warning("initHrefAndRegion error");
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получить города и соответствующие url
     */
    //FIXME Update regex
    private void initAllCities(BasicSiteService<GismeteoEntity> gismeteoService) {
        initHrefAndRegion();
        multiThreadInit(hrefAndRegionExt);
    }

    private void multiThreadInit(final List<DistrictRegionUrl> hrefAndRegionExt) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Runnable> tasks = new ArrayList<>();

        int elementCount = hrefAndRegionExt.size();
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

            List<DistrictRegionUrl> part = hrefAndRegionExt.subList(start, end);
            tasks.add(() -> {
                log.info(this.getClass().getName() + " " + Thread.currentThread().getName() + " start");
                for (DistrictRegionUrl d : part) {
                    Document doc;
                    try {
                        doc = Jsoup.connect(d.getUrl()).get();
                        Elements citiesInRegion = doc.select(".catalog_block")
                                .select(".catalog_list")
                                .select("a");

                        for (Element el : citiesInRegion) {
                            GismeteoEntity gismeteoEntity = new GismeteoEntity();
                            String city = el.text();
                            gismeteoEntity.setCity(city);
                            gismeteoEntity.setRegion(d.getRegion());
                            gismeteoEntity.setDistrict(d.getDistrict());
                            gismeteoEntity.setFullLocation(city + " " + d);
                            gismeteoEntity.setUrl("https://www.gismeteo.ua" + el.attr("href"));
                            gismeteoEntity.setCreatedAt(LocalDateTime.now());
                            gismeteoService.save(gismeteoEntity);
                        }
                    } catch (IOException e) {
                        log.warning("initAllCities error");
                        e.printStackTrace();
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

    public BasicSiteService<GismeteoEntity> getGismeteoService() {
        return gismeteoService;
    }
}

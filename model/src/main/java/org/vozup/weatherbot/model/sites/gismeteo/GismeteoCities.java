package org.vozup.weatherbot.model.sites.gismeteo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.vozup.weatherbot.model.services.entities.GismeteoEntity;
import org.vozup.weatherbot.model.services.service.BasicSiteService;
import org.vozup.weatherbot.model.sites.BasicCities;
import org.vozup.weatherbot.model.sites.DistrictRegionPair;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.logging.Logger;

public class GismeteoCities implements BasicCities {
    private static Logger log = Logger.getLogger(GismeteoCities.class.getName());

    private HashMap<String, String> hrefAndRegion;
    private HashMap<DistrictRegionPair, String> hrefAndRegionExt;
    private final BasicSiteService<GismeteoEntity> gismeteoService;

    public GismeteoCities(BasicSiteService<GismeteoEntity> gismeteoService) {
        hrefAndRegion = new HashMap<>();
        hrefAndRegionExt = new HashMap<>();
        this.gismeteoService = gismeteoService;
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
                        hrefAndRegionExt.put(new DistrictRegionPair(district, region),
                                "https://www.gismeteo.ua" + el.attr("href"));
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

        hrefAndRegionExt.forEach((districtRegion, url) -> {
            Document doc;
            try {
                doc = Jsoup.connect(url).get();
                Elements citiesInRegion = doc.select(".catalog_block")
                        .select(".catalog_list")
                        .select("a");

                for (Element el : citiesInRegion) {
                    GismeteoEntity gismeteoEntity = new GismeteoEntity();
                    String city = el.text();
                    gismeteoEntity.setCity(city);
                    gismeteoEntity.setRegion(districtRegion.getRegion());
                    gismeteoEntity.setDistrict(districtRegion.getDistrict());
                    gismeteoEntity.setFullLocation(city + " " + districtRegion);
                    gismeteoEntity.setUrl("https://www.gismeteo.ua" + el.attr("href"));
                    gismeteoEntity.setCreatedAt(LocalDateTime.now());
                    gismeteoService.save(gismeteoEntity);
                }
            } catch (IOException e) {
                log.warning("initAllCities error");
                e.printStackTrace();
            }
        });
    }

    public BasicSiteService<GismeteoEntity> getGismeteoService() {
        return gismeteoService;
    }
}

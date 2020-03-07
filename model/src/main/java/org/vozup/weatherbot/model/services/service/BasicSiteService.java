package org.vozup.weatherbot.model.services.service;

import org.vozup.weatherbot.model.services.Sites;

import java.util.List;

public interface BasicSiteService<T> {
    void save(T entity);

    T get(Long id);

    List<T> findAll();

    List<T> findAllByRegion(String region);

    List<T> findAllByDistrict(String district);

    List<T> findByCity(String city);

    List<T> findAllByRegion(String region, Sites site);

    List<T> findAllByDistrict(String district, Sites site);

    List<T> findByCity(String city, Sites site);
}

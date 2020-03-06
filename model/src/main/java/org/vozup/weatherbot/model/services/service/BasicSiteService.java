package org.vozup.weatherbot.model.services.service;

import java.util.List;

public interface BasicSiteService<T> {
    void save(T entity);

    T get(Long id);

    List<T> findAll();

    List<T> findAllByRegion(String region);

    List<T> findAllByDistrict(String district);
}

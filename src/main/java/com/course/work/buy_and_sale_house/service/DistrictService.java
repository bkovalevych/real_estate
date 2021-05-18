package com.course.work.buy_and_sale_house.service;

import com.course.work.buy_and_sale_house.entity.District;

import java.util.List;

public interface DistrictService {
    List<District> findAllDistricts();
    void create(String district);
    void save(District d);
    List<District> loadTestData();
    void deleteAll(Iterable<? extends District> iterable);
    void deleteTestData();
    void deleteById(Long id);
    District findById(Long id);
}

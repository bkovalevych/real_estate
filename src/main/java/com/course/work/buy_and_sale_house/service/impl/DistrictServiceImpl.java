package com.course.work.buy_and_sale_house.service.impl;

import com.course.work.buy_and_sale_house.entity.District;
import com.course.work.buy_and_sale_house.repository.DistrictRepository;
import com.course.work.buy_and_sale_house.service.DistrictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {

    private DistrictRepository districtRepository;

    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Override
    public List<District> findAllDistricts() {
        return districtRepository.findAllByHidden(false);
    }

    @Override
    public void create(String districtName) {
        District district = new District(districtName);
        districtRepository.save(district);
    }

    @Override
    public void save(District d) {
        districtRepository.save(d);
    }

    @Override
    public List<District> loadTestData() {
        String[] districtsNames = new String[] {
                "Індустріальний_",
                "Київський_",
                "Новобаварський_",
                "Основ'янський_",
                "Слобідський_",
                "Холодногірський_",
                "Шевченківський_"
        };
        List<District> districts = new ArrayList<>();
        for (String name : districtsNames) {
            District new_district = new District(name);
            districtRepository.save(new_district);
            districts.add(new_district);
        }
        return districts;
    }

    @Override
    public void deleteAll(Iterable<? extends District> iterable) {
        districtRepository.deleteAll(iterable);
    }

    @Override
    public void deleteTestData() {
        List<District> d = districtRepository.findAllByNameIsLike("%_%");
        deleteAll(d);
    }

    @Override
    public District findById(Long id) {
        return districtRepository.findOneById(id);
    }

    @Override
    public void deleteById(Long id) {
        District d = districtRepository.findOneById(id);
        if (d != null) {
            d.setHidden(true);
            districtRepository.save(d);
        }
    }
}

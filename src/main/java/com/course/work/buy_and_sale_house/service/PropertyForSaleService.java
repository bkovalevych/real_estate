package com.course.work.buy_and_sale_house.service;

import com.course.work.buy_and_sale_house.entity.District;
import com.course.work.buy_and_sale_house.entity.PropertyForSale;
import com.course.work.buy_and_sale_house.entity.RequestToBuy;
import com.course.work.buy_and_sale_house.entity.User;

import java.util.List;

public interface PropertyForSaleService {
    static final String STATUS_SOLD = "Продано";
    static final String STATUS_HIDDEN = "Приховано";
    static final String STATUS_OK = "Відкрито";
    static final String TYPE_APARTMENT = "Квартира";
    static final String TYPE_HOUSE = "Будинок";

    PropertyForSale findById(Long id);
    List<PropertyForSale> findAllByUserAndStatusIs(User user, String status);
    List<PropertyForSale> findAppropriate(RequestToBuy requestToBuy, boolean isOwn);
    void addRequestToBuy(RequestToBuy requestToBuy, PropertyForSale propertyForSale);
    void deleteById(Long id);
    void save(PropertyForSale propertyForSale);
    List<PropertyForSale> loadTestData(List<District> districts, User user, int count);
    void deleteAll(Iterable<? extends PropertyForSale> iterable);
    List<PropertyForSale> findAllByUser(User user);
    void addCandidate(PropertyForSale sale, RequestToBuy req);

}

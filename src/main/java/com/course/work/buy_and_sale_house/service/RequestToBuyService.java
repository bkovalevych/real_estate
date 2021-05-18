package com.course.work.buy_and_sale_house.service;

import com.course.work.buy_and_sale_house.entity.District;
import com.course.work.buy_and_sale_house.entity.PropertyForSale;
import com.course.work.buy_and_sale_house.entity.RequestToBuy;
import com.course.work.buy_and_sale_house.entity.User;

import java.util.List;

public interface RequestToBuyService {
    static final String STATUS_SOLD = "Продано";
    static final String STATUS_HIDDEN = "Приховано";
    static final String STATUS_OK = "Відкрито";
    static final String TYPE_APARTMENT = "Квартира";
    static final String TYPE_HOUSE = "Будинок";
    RequestToBuy findById(Long id);
    List<RequestToBuy> findAllByUserAndStatusIs(User user, String status);
    List<RequestToBuy> findAppropriate(PropertyForSale propertyForSale, boolean isOwn);
    void addPropertyForSale(PropertyForSale propertyForSale, RequestToBuy requestToBuy);
    void deleteById(Long id);
    void save(RequestToBuy requestToBuy);
    List<RequestToBuy> loadTestData(List<District> districts, User user, int count);
    void deleteAll(Iterable<? extends RequestToBuy> iterable);
    List<RequestToBuy> findAllByUser(User user);
}

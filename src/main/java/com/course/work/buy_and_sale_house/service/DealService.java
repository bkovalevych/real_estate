package com.course.work.buy_and_sale_house.service;

import com.course.work.buy_and_sale_house.entity.Deal;
import com.course.work.buy_and_sale_house.entity.PropertyForSale;
import com.course.work.buy_and_sale_house.entity.RequestToBuy;
import com.course.work.buy_and_sale_house.entity.User;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

public interface DealService {
    static final String ACCEPTED = "Прийнято";
    static final String DECLINED = "Відхилено";
    static final String PENDING = "В очікуванні";

    Deal acceptDealByTwoSides(RequestToBuy buy, PropertyForSale sale);
    void initDeal(Deal deal);
    void saveDeal(Deal deal);
    List<Deal> findAllByBuyer(User buyer);
    List<Deal> findAllBySeller(User seller);
    Deal findDealById(Long id);
    void acceptDeal(Deal deal, User user);
    void declineDeal(Deal deal, User user);
    List<Deal> getReport(Date start, Date finish);
    ByteArrayInputStream saveReport(Date start, Date finish);
    void deleteAll(Iterable<? extends Deal> iterable);
    List<Deal> loadTestData(List<RequestToBuy> buying, List<PropertyForSale> sales, int count);
}

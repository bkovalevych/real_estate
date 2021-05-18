package com.course.work.buy_and_sale_house.service;

import com.course.work.buy_and_sale_house.entity.Commission;

import java.util.Date;
import java.util.List;

public interface CommissionService {

    Commission getActualCommission();
    void deleteAll(Iterable<? extends Commission> iterable);
    void save(double new_commission, Date date);
    void save(double new_commission);
    List<Commission> findAll();
}

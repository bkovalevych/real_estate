package com.course.work.buy_and_sale_house.service.impl;

import com.course.work.buy_and_sale_house.constant.Constant;
import com.course.work.buy_and_sale_house.entity.Commission;
import com.course.work.buy_and_sale_house.repository.CommissionRepository;
import com.course.work.buy_and_sale_house.service.CommissionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommissionServiceImpl implements CommissionService {
    private final CommissionRepository repository;

    public CommissionServiceImpl(CommissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(double new_commission, Date date) {
        Commission commission = new Commission();
        commission.setCommission(new_commission);
        commission.setIsRealFrom(new java.sql.Date(date.getTime()));
        repository.deleteAll(repository.findAll());
        repository.save(commission);
    }

    @Override
    public Commission getActualCommission() {
        Commission commission = repository.getActualCommission().get(0);
        if (commission == null) {
            commission = new Commission();
            commission.setIsRealFrom(new java.sql.Date(new Date().getTime()));
            commission.setCommission(Constant.DEFAULT_COMMISSION);
            repository.save(commission);
        }
        return commission;
    }

    @Override
    public void save(double new_commission) {
        Commission commission = new Commission(new_commission);
        commission.setIsRealFrom(new java.sql.Date(new Date().getTime()));
        repository.deleteAll(repository.findAll());
        repository.save(commission);
    }

    @Override
    public void deleteAll(Iterable<? extends Commission> iterable) {
        repository.deleteAll(iterable);
    }

    @Override
    public List<Commission> findAll() {
        return repository.findAllByOrderByIsRealFrom();
    }
}

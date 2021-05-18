package com.course.work.buy_and_sale_house.repository;

import com.course.work.buy_and_sale_house.entity.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommissionRepository extends JpaRepository<Commission, Long> {

    @Query("SELECT c FROM Commission c where c.commission = (select max(c1.commission) FROM Commission c1)")
    List<Commission> getActualCommission();

    @Override
    void deleteAll(Iterable<? extends Commission> iterable);


    List<Commission> findAllByOrderByIsRealFrom();
}

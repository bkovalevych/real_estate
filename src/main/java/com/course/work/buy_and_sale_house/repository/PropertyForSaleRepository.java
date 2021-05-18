package com.course.work.buy_and_sale_house.repository;

import com.course.work.buy_and_sale_house.entity.PropertyForSale;
import com.course.work.buy_and_sale_house.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PropertyForSaleRepository extends JpaRepository<PropertyForSale, Long>, JpaSpecificationExecutor<PropertyForSale> {
    @Override
    void deleteAll(Iterable<? extends PropertyForSale> iterable);

    List<PropertyForSale> findAllByUserAndStatusIs(User user, String status);
    List<PropertyForSale> findAllByUser(User user);
}

package com.course.work.buy_and_sale_house.repository;

import com.course.work.buy_and_sale_house.entity.Deal;
import com.course.work.buy_and_sale_house.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findAllByBuyerOrderByDateOfDeal(User buyer);
    List<Deal> findAllBySellerOrderByDateOfDeal(User seller);

    List<Deal> findAllByDateOfDealBetweenAndStatus(Date from, Date to, String status);
    @Query("SELECT SUM(d.commission.commission * d.propertyForSale.price) FROM Deal d WHERE d.dateOfDeal between ?1 AND ?2")
    Double getTotal(Date fromDate, Date toDate);

    @Override
    void deleteAll(Iterable<? extends Deal> iterable);


}

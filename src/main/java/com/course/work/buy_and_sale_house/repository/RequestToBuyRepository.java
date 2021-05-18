package com.course.work.buy_and_sale_house.repository;

import com.course.work.buy_and_sale_house.entity.RequestToBuy;
import com.course.work.buy_and_sale_house.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RequestToBuyRepository extends JpaRepository<RequestToBuy, Long>, JpaSpecificationExecutor<RequestToBuy> {
    @Override
    void deleteAll(Iterable<? extends RequestToBuy> iterable);

    List<RequestToBuy> findAllByUserAndStatusIsOrderByDateAsc(User user, String Status);
    List<RequestToBuy> findAllByUser(User user);
}

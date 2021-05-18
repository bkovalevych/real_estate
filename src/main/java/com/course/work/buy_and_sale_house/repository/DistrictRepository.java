package com.course.work.buy_and_sale_house.repository;

import com.course.work.buy_and_sale_house.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    @Override
    void deleteAll(Iterable<? extends District> iterable);

    @Override
    List<District> findAll();

    District findOneById(Long id);

    List<District> findAllByHidden(boolean isDeleted);



    List<District> findAllByNameIsLike(String parameter);

}

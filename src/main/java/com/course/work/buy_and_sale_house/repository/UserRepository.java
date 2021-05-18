package com.course.work.buy_and_sale_house.repository;

import com.course.work.buy_and_sale_house.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    void deleteAll(Iterable<? extends User> iterable);

    User findByUsername(String username);

    @Override
    void deleteById(Long id);

    List<User> findAllByOrderByUsername();
}

package com.course.work.buy_and_sale_house.service;

import com.course.work.buy_and_sale_house.entity.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);
    List<User> getUsers();
    void createUser(User user);
    void deleteAll(Iterable<? extends User> iterable);
    void deleteById(Long id);
}

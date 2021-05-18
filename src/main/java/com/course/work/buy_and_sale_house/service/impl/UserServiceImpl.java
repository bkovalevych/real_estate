package com.course.work.buy_and_sale_house.service.impl;

import com.course.work.buy_and_sale_house.entity.User;
import com.course.work.buy_and_sale_house.repository.UserRepository;
import com.course.work.buy_and_sale_house.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAllByOrderByUsername();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {
        userRepository.deleteAll(iterable);
    }
}

package com.course.work.buy_and_sale_house.controller;

import com.course.work.buy_and_sale_house.constant.Constant;
import com.course.work.buy_and_sale_house.entity.User;
import com.course.work.buy_and_sale_house.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping(Constant.REGISTER)
    public ModelAndView registerPage() {
        return new ModelAndView(Constant.REGISTER);
    }

    @PostMapping(Constant.REGISTER)
    public ModelAndView register(@RequestParam String username, @RequestParam String password,
                                 @RequestParam String firstName, @RequestParam String lastName,
                                 @RequestParam String phoneNumber, HttpServletRequest request) {
        User user = new User(username, password, firstName, lastName, phoneNumber);

        try {
            userService.createUser(user);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView(Constant.REGISTER);
            modelAndView.addObject("successRegister", "Користувач з логіном " + username
                    + " вже існує! Спробуйте інший логін.");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("redirect:" + Constant.ROOT);
        initUser(request, user);
        request.getSession().setAttribute("successRegister", "Успішна реєстрація");
        return modelAndView;
    }

    @GetMapping(Constant.USERS)
    public ModelAndView getUsers() {
        ModelAndView modelAndView = new ModelAndView(Constant.USERS);
        modelAndView.addObject("users", userService.getUsers());
        return modelAndView;
    }

    @GetMapping(Constant.LOGIN)
    public ModelAndView loginPage() {

        return new ModelAndView("redirect:/");
    }

    @GetMapping(Constant.LOGOUT)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }

    private void initUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute(Constant.ROLE, Constant.ROLE_USER);
        System.out.println(request.getSession().getAttribute("user"));
        request.getSession().setMaxInactiveInterval(60 * 10);
    }

    @PostMapping(Constant.LOGIN)
    public ModelAndView logIn(@RequestParam String username, @RequestParam String password,
                              HttpServletRequest request) {
        User user = userService.findUserByUsername(username);

        ModelAndView modelAndView;
        if (user != null && user.getPassword().equals(password)) {
            modelAndView = new ModelAndView("redirect:" + Constant.ROOT);
            initUser(request, user);
        } else {
            modelAndView = new ModelAndView("index");
            modelAndView.addObject("incorrectCredentials", "Неправильні дані");
        }
        return modelAndView;

    }

    @GetMapping(Constant.ROOT)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView m = new ModelAndView("index");
        m.addObject("prefix", "");
        return m;
    }
}

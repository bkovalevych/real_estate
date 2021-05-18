package com.course.work.buy_and_sale_house.controller;

import com.course.work.buy_and_sale_house.constant.Constant;
import com.course.work.buy_and_sale_house.entity.Deal;
import com.course.work.buy_and_sale_house.entity.User;
import com.course.work.buy_and_sale_house.service.DealService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(Constant.DEAL_PATH)
public class DealController {
    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping
    public ModelAndView getDeals(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        ModelAndView m = new ModelAndView(Constant.DEAL_PATH + "/index");
        if (user == null) {
            return m;
        }
        List<Deal> dealsSeller = dealService.findAllBySeller(user);
        List<Deal> dealsBuyer = dealService.findAllByBuyer(user);
        m.addObject("dealsSeller", dealsSeller);
        m.addObject("dealsBuyer", dealsBuyer);

        return m;
    }
}

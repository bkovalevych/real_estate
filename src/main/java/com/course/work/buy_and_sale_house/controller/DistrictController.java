package com.course.work.buy_and_sale_house.controller;

import com.course.work.buy_and_sale_house.constant.Constant;
import com.course.work.buy_and_sale_house.entity.Deal;
import com.course.work.buy_and_sale_house.entity.District;
import com.course.work.buy_and_sale_house.service.DistrictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(Constant.DISTRICT_PATH)
public class DistrictController {
    final
    DistrictService dService;

    public DistrictController(DistrictService dService) {
        this.dService = dService;
    }

    @GetMapping("")
    ModelAndView getDistricts() {
        ModelAndView m = new ModelAndView(Constant.DISTRICT_PATH + "/index");
        m.addObject("districts", dService.findAllDistricts());
        return m;
    }

    @GetMapping("/{id}")
    public ModelAndView changeById(@PathVariable("id") District district) {
        ModelAndView m = new ModelAndView(Constant.DISTRICT_PATH + "/details");
        m.addObject("district", district);
        return m;
    }
    @PostMapping("/{id}")
    public String changeFunction(@PathVariable("id")District district,
                                 @RequestParam("streetName")String name ) {
        district.setName(name);
        dService.save(district);
        return "redirect:" + Constant.DISTRICT_PATH;
    }

    @PostMapping("")
    ModelAndView postDistrict(@RequestParam String name) {
        ModelAndView m = new ModelAndView(Constant.DISTRICT_PATH + "/index");
        List<District> districts = dService.findAllDistricts();
        if (name != null) {
            District district = new District();
            district.setName(name);
            if (!districts.contains(district)) {
                dService.save(district);
                districts.add(district);
            } else {
                m.addObject("message", "Цей район вже існує");
            }
        }
        m.addObject("districts", districts);
        return m;
    }

    @PostMapping("/delete")
    ModelAndView deleteDistrict(@RequestParam Long id) {
        ModelAndView m = new ModelAndView(Constant.DISTRICT_PATH + "/index");
        if (id != null) {
            dService.deleteById(id);
            m.addObject("message", "район з ID "+ id +" бів видалений");
        }
        m.addObject("districts", dService.findAllDistricts());
        return m;
    }

}

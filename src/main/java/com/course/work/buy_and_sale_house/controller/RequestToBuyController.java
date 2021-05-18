package com.course.work.buy_and_sale_house.controller;

import com.course.work.buy_and_sale_house.constant.Constant;
import com.course.work.buy_and_sale_house.entity.District;
import com.course.work.buy_and_sale_house.entity.PropertyForSale;
import com.course.work.buy_and_sale_house.entity.RequestToBuy;
import com.course.work.buy_and_sale_house.entity.User;
import com.course.work.buy_and_sale_house.service.DistrictService;
import com.course.work.buy_and_sale_house.service.RequestToBuyService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(Constant.REQUEST_TO_BUY_PATH)
public class RequestToBuyController {
    final
    RequestToBuyService requestToBuyService;

    final DistrictService districtService;

    public RequestToBuyController(RequestToBuyService requestToBuyService, DistrictService districtService) {
        this.requestToBuyService = requestToBuyService;
        this.districtService = districtService;
    }

    @PostMapping("")
    public ModelAndView addRequestToBuy(
            @RequestParam(value = "district", required = false)List<District> chosenDistricts,
            @RequestParam(value = "minArea", required = false)Double minArea,
            @RequestParam(value = "maxArea", required = false)Double maxArea,
            @RequestParam(value = "minPrice", required = false)Double minPrice,
            @RequestParam(value = "maxPrice", required = false)Double maxPrice,
            @RequestParam(value = "numberOfRooms", required = false)Integer numberOfRooms,
            @RequestParam(value = "type", required = false)String type,
            @RequestParam(value = "description", required = false)String description,
            @RequestParam(value = "fullDescription", required = false)String fullDescription,
            @RequestParam(value = "saleChosenByUser", required = false)PropertyForSale propertyForSale,
            HttpServletRequest request
    ) {
        User user = (User) request.getSession().getAttribute("user");
        boolean isOk = user != null;
        RequestToBuy requestToBuy = formRequestToBuy(null,
                chosenDistricts,
                minArea, maxArea,
                minPrice, maxPrice,
                numberOfRooms, type,
                description, fullDescription);
        if (isOk)
        try {
            requestToBuy.setUser(user);
            requestToBuy.setDate(new java.sql.Date(new Date().getTime()));
            requestToBuy.setSaleChosenByUser(propertyForSale);
            requestToBuy.setStatus(RequestToBuyService.STATUS_OK);
            requestToBuyService.save(requestToBuy);
        } catch (Exception ex) {
            isOk = false;
        }
        ModelAndView m = root(null, null, null, null, null, user, true);
        if (isOk) {
            m.addObject("msgAdded", "Ваша пропозиція збережена");
        } else {
            m.addObject("msgNotAdded", "Помилка при збереженні пропозиції!");
        }
        return m;
    }

    @GetMapping("")
    public ModelAndView getRoot(
            @RequestParam(required = false) Double area,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long district,
            @RequestParam(required = false) Integer numberOfRooms,
            @RequestParam(required = false, defaultValue = "false") boolean isMy,
            HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return root(area, price, type, district, numberOfRooms, user, isMy);
    }

    @GetMapping("/{id}")
    public ModelAndView getAnotherRoot(
            @PathVariable("id") Long id,
            @RequestParam(value = "isMy", defaultValue="true") boolean isMy,
            HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        ModelAndView m = new ModelAndView(Constant.REQUEST_TO_BUY_PATH + "/details");
        m.addObject("user", user);
        RequestToBuy requestToBuy = requestToBuyService.findById(id);
        request.getSession().setAttribute("reg", requestToBuy);
        m.addObject("req", requestToBuy);
        if (isMy && requestToBuy != null && requestToBuy.getUser().equals(user)) {
            m.addObject("isMy", true);
        }
        return m;
    }
    @PostMapping("/{id}/changeValues")
    public String changeValues(
            @PathVariable("id")RequestToBuy requestToBuy,
            @RequestParam(value = "district", required = false)List<District> chosenDistricts,
            @RequestParam(value = "minArea", required = false)Double minArea,
            @RequestParam(value = "maxArea", required = false)Double maxArea,
            @RequestParam(value = "minPrice", required = false)Double minPrice,
            @RequestParam(value = "maxPrice", required = false)Double maxPrice,
            @RequestParam(value = "numberOfRooms", required = false)Integer numberOfRooms,
            @RequestParam(value = "type", required = false)String type,
            @RequestParam(value = "description", required = false)String description,
            @RequestParam(value = "fullDescription", required = false)String fullDescription,
            HttpServletRequest request
    ) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null || !user.equals(requestToBuy.getUser())) {
            return "redirect:" + Constant.REQUEST_TO_BUY_PATH;
        }
        requestToBuy = formRequestToBuy(requestToBuy,
                chosenDistricts,
                minArea, maxArea,
                minPrice, maxPrice,
                numberOfRooms, type,
                description, fullDescription);
        requestToBuyService.save(requestToBuy);
        return "redirect:" + Constant.REQUEST_TO_BUY_PATH + "/" + requestToBuy.getId() + "/?isMy=true";
    }

    private RequestToBuy formRequestToBuy(RequestToBuy requestToBuy,
                                          List<District> chosenDistricts,
                                          Double minArea, Double maxArea,
                                          Double minPrice, Double maxPrice,
                                          Integer numberOfRooms, String type,
                                          String description, String fullDescription) {
        if (requestToBuy == null) {
            requestToBuy = new RequestToBuy();
        }
        requestToBuy.setDistrict(chosenDistricts);
        if (chosenDistricts == null || chosenDistricts.isEmpty()) {
            requestToBuy.setDistrictNoMatter(true);
        }
        requestToBuy.setMinArea(minArea);
        requestToBuy.setMaxArea(maxArea);
        if (maxArea == null && minArea == null) {
            requestToBuy.setAreaNoMatter(true);
        }
        requestToBuy.setMinPrice(minPrice);
        requestToBuy.setMaxPrice(maxPrice);
        if (maxPrice == null && minPrice == null) {
            requestToBuy.setPriceNoMatter(true);
        }
        if (numberOfRooms == null) {
            requestToBuy.setNumberOfRoomsNoMatter(true);
        } else {
            requestToBuy.setNumberOfRooms(numberOfRooms);
        }
        requestToBuy.setType(type);
        requestToBuy.setDescription(description);
        requestToBuy.setFullDescription(fullDescription);
        return requestToBuy;
    }

    @PostMapping("/{id}/changeStatus")
    public String changeStatus(
            @PathVariable("id")RequestToBuy requestToBuy,
            @RequestParam("status")String status,
            HttpServletRequest request
    ) {
        User user = (User)request.getSession().getAttribute("user");
        if (requestToBuy == null || user == null || !user.equals(requestToBuy.getUser())) {
            return "redirect:" + Constant.REQUEST_TO_BUY_PATH + "/?isMy=true";
        }
        requestToBuy.setStatus(status);
        requestToBuyService.save(requestToBuy);
        return "redirect:" + Constant.REQUEST_TO_BUY_PATH +"/" + requestToBuy.getId() + "?isMy=true";
    }

    @PostMapping("/{id}/unselect")
    public String unselect(@PathVariable("id")RequestToBuy requestToBuy) {
        requestToBuy.setSaleChosenByUser(null);
        requestToBuyService.save(requestToBuy);
        return "redirect:" + Constant.REQUEST_TO_BUY_PATH +"/" + requestToBuy.getId() + "?isMy=true";
    }

    @PostMapping("/{id}/chooseSale")
    public String chooseSale(@PathVariable("id") RequestToBuy requestToBuy,
                             @RequestParam("saleId") PropertyForSale propertyForSale)
    {
        requestToBuy.setSaleChosenByUser(propertyForSale);
        requestToBuyService.save(requestToBuy);
        return "redirect:" + Constant.REQUEST_TO_BUY_PATH +"/" + requestToBuy.getId() + "?isMy=true";
    }

    private ModelAndView root(
            Double area,
            Double price,
            String type,
            Long districtId,
            Integer numberOfRooms,
            User user,
            boolean isMy) {
        District district = districtService.findById(districtId);
        List<District> districts = districtService.findAllDistricts();
        ModelAndView m = new ModelAndView(Constant.REQUEST_TO_BUY_PATH + "/index");
        m.addObject("districts", districts);
        m.addObject("district", district);
        m.addObject("area", area);
        m.addObject("price", price);
        m.addObject("type", type);
        m.addObject("numberOfRooms", numberOfRooms);
        PropertyForSale prop = new PropertyForSale();
        if (user != null) {
            prop.setUser(user);
        }
        prop.setType(type);
        prop.setPrice(price);
        prop.setArea(area);
        prop.setDistrict(district);
        prop.setNumberOfRooms(numberOfRooms);
        List<RequestToBuy> requests = requestToBuyService.findAppropriate(prop, isMy);
        m.addObject("requests", requests);
        m.addObject("isMy", isMy);
        return m;
    }


}

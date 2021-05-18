package com.course.work.buy_and_sale_house.controller;

import com.course.work.buy_and_sale_house.constant.Constant;
import com.course.work.buy_and_sale_house.entity.*;
import com.course.work.buy_and_sale_house.service.DealService;
import com.course.work.buy_and_sale_house.service.DistrictService;
import com.course.work.buy_and_sale_house.service.PropertyForSaleService;
import com.course.work.buy_and_sale_house.service.RequestToBuyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(Constant.PROPERTY_FOR_SALE_PATH)
public class PropertyForSaleController {
    private final PropertyForSaleService propertyForSaleService;
    private final DistrictService districtService;
    private final RequestToBuyService requestToBuyService;
    private final DealService dealService;

    public PropertyForSaleController(PropertyForSaleService propertyForSaleService,
                                     DistrictService districtService,
                                     RequestToBuyService requestToBuyService, DealService dealService) {

        this.propertyForSaleService = propertyForSaleService;
        this.districtService = districtService;
        this.requestToBuyService = requestToBuyService;
        this.dealService = dealService;
    }

    @GetMapping("")
    public ModelAndView getRoot(
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) List<District> selectedDistricts,
            @RequestParam(required = false, defaultValue = "false") boolean isMy,
            @RequestParam(required = false) Integer numberOfRooms,
            HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return root(minArea, maxArea, minPrice, maxPrice, type, selectedDistricts, numberOfRooms, user, isMy);
    }

    @PostMapping("")
    public ModelAndView add(
            @RequestParam Double area,
            @RequestParam Double price,
            @RequestParam String type,
            @RequestParam District district,
            @RequestParam Integer numberOfRooms,
            @RequestParam String description,
            @RequestParam String fullDescription,
            @RequestParam String address,
            @RequestParam(required = false, defaultValue = "false") boolean isMy,
            HttpServletRequest request) {

        User user = (User)request.getSession().getAttribute("user");
        PropertyForSale propertyForSale = new PropertyForSale(
                user,
                district,
                area,
                price,
                numberOfRooms,
                address,
                type,
                description,
                fullDescription
        );
        boolean isOk = true;
        try {
            propertyForSaleService.save(propertyForSale);
        } catch (Exception e) {
            isOk = false;
        }
        ModelAndView m = root(null, null, null, null,
                null, null, null,
                user, true);
        if (isOk) {
            m.addObject("msgSaleAdded", "Продаж було додано");
        } else {
            m.addObject("msgSaleNotAdded", "Помилка при додаванні продажу!");
        }
        return m;
    }

    @GetMapping("/")
    public ModelAndView getAnotherRoot(
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) List<District> selectedDistricts,
            @RequestParam(required = false) Integer numberOfRooms,
            @RequestParam(required = false, defaultValue = "false") boolean isMy,
            HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return root(minArea, maxArea, minPrice, maxPrice, type, selectedDistricts, numberOfRooms, user, isMy);
    }

    @PostMapping("/{id}/changeValues")
    public String changeValues(
            @PathVariable("id")PropertyForSale propertyForSale,
            @RequestParam("district")District district,
            @RequestParam("address")String address,
            @RequestParam("price")Double price,
            @RequestParam("area")Double area,
            @RequestParam("numberOfRooms")Integer numberOfRooms,
            @RequestParam("type")String type,
            @RequestParam("description")String description,
            @RequestParam("fullDescription")String fullDescription,
            HttpServletRequest request
    ) {
        User user = (User)request.getSession().getAttribute("user");
        if (district == null || propertyForSale == null || user == null || !user.equals(propertyForSale.getUser())) {
            return "redirect:" + Constant.PROPERTY_FOR_SALE_PATH;
        }
        propertyForSale.setDistrict(district);
        propertyForSale.setAddress(address);
        propertyForSale.setPrice(price);
        propertyForSale.setArea(area);
        propertyForSale.setNumberOfRooms(numberOfRooms);
        propertyForSale.setType(type);
        propertyForSale.setDescription(description);
        propertyForSale.setFullDescription(fullDescription);
        for (RequestToBuy requestToBuy: propertyForSale.getCandidates()) {
            if (requestToBuy.getSelectedSale() != null) {
                requestToBuy.getSelectedSale().remove(propertyForSale);
                requestToBuyService.save(requestToBuy);
            }
        }
        propertyForSale.setCandidates(new ArrayList<>());

        propertyForSaleService.save(propertyForSale);
        return "redirect:" + Constant.PROPERTY_FOR_SALE_PATH + "/"+propertyForSale.getId() +"?isMy=true";
    }

    @PostMapping("/{id}/changeStatus")
    public String changeStatus(
            @PathVariable("id")PropertyForSale propertyForSale,
            @RequestParam("status")String status,
            HttpServletRequest request
    ) {
        User user = (User)request.getSession().getAttribute("user");
        if (propertyForSale == null || user == null || !user.equals(propertyForSale.getUser())) {
            return "redirect:" + Constant.PROPERTY_FOR_SALE_PATH + "/?isMy=true";
        }
        propertyForSale.setStatus(status);
        propertyForSaleService.save(propertyForSale);
        return "redirect:" + Constant.PROPERTY_FOR_SALE_PATH + "/"+propertyForSale.getId() +"?isMy=true";
    }

    @PostMapping("/{id}/deleteCandidate")
    public String deleteCandidate(@PathVariable("id")PropertyForSale propertyForSale,
                                  @RequestParam("requestId") RequestToBuy requestToBuy,
                                  HttpServletRequest request) {
        User owner = (User)request.getSession().getAttribute("user");
        if (owner == null || propertyForSale == null || requestToBuy == null
                || !owner.equals(propertyForSale.getUser())) {
            return "redirect:" + Constant.PROPERTY_FOR_SALE_PATH + "/?isMy=true";
        }
        if (propertyForSale.getCandidates() == null || propertyForSale.getCandidates().isEmpty()) {
            return "redirect:" + Constant.PROPERTY_FOR_SALE_PATH + "/?isMy=true";
        }
        propertyForSale.getCandidates().remove(requestToBuy);
        if (requestToBuy.getSelectedSale() != null) {
            requestToBuy.getSelectedSale().remove(propertyForSale);
            requestToBuyService.save(requestToBuy);
        }
        propertyForSaleService.save(propertyForSale);
        return "redirect:" + Constant.PROPERTY_FOR_SALE_PATH + "/" + propertyForSale.getId() + "?isMy=true";
    }

    @PostMapping("/{id}/deal")
    public ModelAndView deal(@PathVariable("id")PropertyForSale propertyForSale,
                               @RequestParam("requestId") RequestToBuy requestToBuy,
                               HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        ModelAndView m = getById(propertyForSale.getId(), true, request);
        if (user == null || !user.equals(propertyForSale.getUser())) {
            return m;
        }
        Deal d = dealService.acceptDealByTwoSides(requestToBuy, propertyForSale);
        m.addObject("deal", d);
        return m;
    }

    @PostMapping("/{id}/addCandidate")
    public String addCandidate(@PathVariable("id")PropertyForSale propertyForSale,
                               @RequestParam("requestId") RequestToBuy requestToBuy,
                               HttpServletRequest request) {
        User owner = (User)request.getSession().getAttribute("user");
        if (owner == null || propertyForSale == null ||
                requestToBuy == null || !owner.equals(propertyForSale.getUser())
        ) {
            return "redirect:" + Constant.PROPERTY_FOR_SALE_PATH + "/?isMy=true";
        }
        List<RequestToBuy> requests = requestToBuyService.findAppropriate(propertyForSale, false);
        propertyForSale.setSelectedRequestToBuy(requests);
        propertyForSaleService.addCandidate(propertyForSale, requestToBuy);
        return "redirect:" + Constant.PROPERTY_FOR_SALE_PATH + "/" + propertyForSale.getId() + "?isMy=true";
    }

    private void updateCandidates(PropertyForSale prop) {
        if (prop.getSelectedRequestToBuy() == null || prop.getCandidates() == null) {
            return;
        }
        for (RequestToBuy req : prop.getCandidates()) {
            prop.getSelectedRequestToBuy().remove(req);
        }
    }

    @GetMapping("/{id}")
    public ModelAndView getById(
            @PathVariable("id") Long id,
            @RequestParam(value = "isMy", defaultValue = "false") boolean isMy,
            HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        ModelAndView m = new ModelAndView(Constant.PROPERTY_FOR_SALE_PATH + "/details");
        m.addObject("user", user);
        PropertyForSale propertyForSale = propertyForSaleService.findById(id);
        List<RequestToBuy> requests = requestToBuyService.findAppropriate(propertyForSale, false);
        propertyForSale.setSelectedRequestToBuy(requests);
        updateCandidates(propertyForSale);
        request.getSession().setAttribute("sale", propertyForSale);
        m.addObject("sale", propertyForSale);
        if (isMy && propertyForSale != null && propertyForSale.getUser().equals(user) &&
                !PropertyForSaleService.STATUS_SOLD.equals(propertyForSale.getStatus())) {
            m.addObject("isMy", true);
        }
        List<District> districts = districtService.findAllDistricts();

        m.addObject("districts", districts);
        return m;
    }


    private ModelAndView root(
            Double minArea,
            Double maxArea,
            Double minPrice,
            Double maxPrice,
            String type,
            List<District> selectedDistricts,
            Integer numberOfRooms,
            User user,
            boolean isOwn) {
        List<District> districts = districtService.findAllDistricts();
        ModelAndView m = new ModelAndView(Constant.PROPERTY_FOR_SALE_PATH + "/index");
        m.addObject("districts", districts);
        m.addObject("selectedDistricts", selectedDistricts);
        m.addObject("minArea", minArea);
        m.addObject("maxArea", maxArea);
        m.addObject("minPrice", minPrice);
        m.addObject("maxPrice", maxPrice);
        m.addObject("numberOfRooms", numberOfRooms);
        m.addObject("type", type);
        m.addObject("isMy", isOwn);

        RequestToBuy req = new RequestToBuy();
        if (user != null) {
            req.setUser(user);
        }
        if (minArea == null && maxArea == null) {
            req.setAreaNoMatter(true);
        }
        if (minPrice == null && maxPrice == null) {
            req.setPriceNoMatter(true);
        }
        if (numberOfRooms == null) {
            req.setNumberOfRoomsNoMatter(true);
        } else {
            req.setNumberOfRooms(numberOfRooms);
        }
        req.setType(type);
        req.setMinPrice(minPrice);
        req.setMaxPrice(maxPrice);
        req.setMinArea(minArea);
        req.setMaxArea(maxArea);
        if (selectedDistricts == null || selectedDistricts.isEmpty()) {
            req.setDistrictNoMatter(true);
            req.setDistrict(new ArrayList<>());
        } else {
            req.setDistrict(selectedDistricts);
        }

        List<PropertyForSale> sales = propertyForSaleService.findAppropriate(req, isOwn);
        m.addObject("sales", sales);
        return m;
    }
}

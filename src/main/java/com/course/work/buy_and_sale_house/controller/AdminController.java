package com.course.work.buy_and_sale_house.controller;

import com.course.work.buy_and_sale_house.constant.Constant;
import com.course.work.buy_and_sale_house.entity.*;
import com.course.work.buy_and_sale_house.service.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(Constant.ADMIN_PATH)
public class AdminController {
    private DealService dealService;
    private UserService userService;
    private DistrictService districtService;
    private PropertyForSaleService propertyForSaleService;
    private RequestToBuyService requestToBuyService;
    private CommissionService commissionService;

    public AdminController(DealService dealService,
                           UserService userService,
                           DistrictService districtService,
                           PropertyForSaleService propertyForSaleService,
                           RequestToBuyService requestToBuyService,
                           CommissionService commissionService
    ) {
        this.userService = userService;
        this.dealService = dealService;
        this.districtService = districtService;
        this.propertyForSaleService = propertyForSaleService;
        this.requestToBuyService = requestToBuyService;
        this.commissionService = commissionService;
    }

    @GetMapping("")
    public ModelAndView empty() {
        return initIndex();
    }

    @GetMapping(Constant.ROOT)
    public ModelAndView index() {
        return initIndex();
    }

    private ModelAndView initIndex() {
        ModelAndView m = new ModelAndView("index");
        m.addObject("prefix", Constant.ADMIN_PATH);
        return m;
    }

    @GetMapping(Constant.LOGIN)
    public ModelAndView loginPage(HttpServletRequest request) {
        request.getSession().setAttribute(Constant.ROLE, Constant.ROLE_ADMIN);
        return initIndex();
    }

    @GetMapping(Constant.LOGOUT)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }

    @PostMapping(Constant.LOGIN)
    public ModelAndView logIn(@RequestParam String username, @RequestParam String password,
                              HttpServletRequest request) {

        ModelAndView modelAndView = initIndex();
        if ("12345".equals(password) && username.equals(Constant.ROLE_ADMIN)) {
            User admin = new User();
            admin.setFirstName("RealAdmin");

            request.getSession().setAttribute("user", admin);
            request.getSession().setAttribute(Constant.ROLE, Constant.ROLE_ADMIN);
            //System.out.println(request.getSession().getAttribute("user"));
            request.getSession().setMaxInactiveInterval(60 * 10);
        } else {
            modelAndView.addObject("incorrectCredentials", "Неправильні дані");
        }
        return modelAndView;
    }

    @GetMapping(Constant.ADMIN_TEST)
    @ResponseBody
    Map<String, Object> adminTest(@RequestParam(required = false) String action) {
        Map<String, Object> result = new HashMap<>();

        result.put("action", action);
        if (action == null) {
            result.put("message", "action = load | delete");
        }
        User mustBeNUll = userService.findUserByUsername("test_buyer");
        User buyer = new User("test_buyer",
                "12345",
                "buyer",
                "buyer",
                "123");
        User seller = new User("test_seller",
                "12345",
                "seller",
                "seller",
                "123");
        if ("load".equals(action) && mustBeNUll != null) {
            result.put("message2", "data is already loaded");
        }

        if ("load".equals(action) && mustBeNUll == null) {
            result.put("data", "loaded");

            userService.createUser(buyer);
            userService.createUser(seller);
            List<District> districts = districtService.loadTestData();
            Date[] dates = new Date[] {new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 5), new Date()};
            Double[] commissionValues = new Double[] {0.1, 0.2};
            for (int i = 0 ; i < dates.length; ++i) {
                Date date = dates[i];
                Double value = commissionValues[i];
                commissionService.save(value, date);
                List<PropertyForSale> sales = propertyForSaleService.loadTestData(districts, seller, 10);
                List<RequestToBuy> requestToBuys = requestToBuyService.loadTestData(districts, buyer, 12 + i);
                List<Deal> deals = dealService.loadTestData(requestToBuys, sales, 5);
                //result.put("deals" + Integer.toString(i), deals);
                //result.put("sales" + Integer.toString(i), sales);
                //result.put("rToBuy" + Integer.toString(i), requestToBuys);
            }

        }
        if ("delete".equals(action)) {
            result.put("data", "deleted");
            buyer = userService.findUserByUsername(buyer.getUsername());
            if (buyer != null) {
                List<Deal> dealsByBuyer = dealService.findAllByBuyer(buyer);
                dealService.deleteAll(dealsByBuyer);
                List<RequestToBuy> rToBuys = requestToBuyService.findAllByUser(buyer);
                requestToBuyService.deleteAll(rToBuys);
                List<PropertyForSale> ps = propertyForSaleService.findAllByUser(buyer);
                propertyForSaleService.deleteAll(ps);
                userService.deleteById(buyer.getId());
            }
            seller = userService.findUserByUsername(seller.getUsername());
            if (seller != null) {
                List<Deal> dealsBySeller = dealService.findAllByBuyer(seller);
                dealService.deleteAll(dealsBySeller);
                List<PropertyForSale> sales = propertyForSaleService.findAllByUser(seller);
                propertyForSaleService.deleteAll(sales);
                List<RequestToBuy> rToBuys = requestToBuyService.findAllByUser(seller);
                requestToBuyService.deleteAll(rToBuys);
                userService.deleteById(seller.getId());
            }
            districtService.deleteTestData();
        }
        return result;
    }





    @GetMapping(Constant.ADMIN_DEALS)
    ModelAndView deals(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date finish
    ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ModelAndView m = new ModelAndView(Constant.ADMIN_PATH + Constant.ADMIN_DEALS);
        m.addObject("deals", dealService.getReport(start, finish));
        String startStr = "";
        String finishStr = "";
        if (start != null) {
            startStr = sdf.format(start);
        }
        if (finish != null) {
            finishStr = sdf.format(finish);
        }

        m.addObject("start", startStr);
        m.addObject("finish", finishStr);
        return m;
    }
    @PostMapping(Constant.ADMIN_DOWNLOAD_DEAL)
    ResponseEntity<Resource> dealsPost(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date finish
    ) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //ModelAndView m = new ModelAndView(Constant.ADMIN_PATH + Constant.ADMIN_DEALS);
        //m.addObject("deals", dealService.getReport(start, finish));
        //m.addObject("start", sdf.format(start));
        //m.addObject("finish", sdf.format(finish));

        String fileName = "report_" + sdf.format(new Date()) + ".xls";
        InputStreamResource file = new InputStreamResource(dealService.saveReport(start, finish));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "atachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @PostMapping(Constant.ADMIN_COMMISSION)
    ModelAndView addCommission(@RequestParam Double commission) {
        ModelAndView m = new ModelAndView("redirect:" + Constant.ADMIN_PATH + Constant.ADMIN_COMMISSION);
        if (commission != null) {
            commissionService.save(commission);
        }
        m.addObject("commissions", commissionService.findAll());
        return m;
    }
    @GetMapping(Constant.ADMIN_COMMISSION)
    ModelAndView getCommission() {
        ModelAndView m = new ModelAndView( Constant.ADMIN_PATH + Constant.ADMIN_COMMISSION);
        m.addObject("commissions", commissionService.findAll());
        return m;
    }

}

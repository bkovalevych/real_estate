package com.course.work.buy_and_sale_house.constant;

public class Constant {
    public static final Double DEFAULT_COMMISSION = 0.02;
    public static final String ROLE = "role";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "user";
    public static final String STATUS_COMPLETED = "completed";
    public static final String STATUS_CANCELED = "canceled";
    public static final String STATUS_ACCEPTED = "accepted";
    public static final String ROOT = "/";
    public static final String PROPERTY_FOR_SALE_PATH = "/propertyForSale";

    public static final String REQUEST_TO_BUY_PATH = "/requestToBuy";

    public static final String DISTRICT_PATH = "/district";

    public static final String DEAL_PATH = "/deal";

    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static  final String USERS = "/users";

    public static final String ADMIN_PATH = "/admin";
    public static final String ADMIN_TEST = "/test";
    public static final String ADMIN_LOGIN = LOGIN;
    public static final String ADMIN_LOGOUT = LOGOUT;
    public static final String ADMIN_REPORT = "/report";
    public static final String ADMIN_DEALS =  "/deals";
    public static final String ADMIN_DOWNLOAD_DEAL =  "/deals/download";
    public static final String ADMIN_PROPERTY_FOR_SALE = ADMIN_PATH + PROPERTY_FOR_SALE_PATH;
    public static final String ADMIN_REQUEST_TO_BUY = ADMIN_PATH + REQUEST_TO_BUY_PATH;
    public static final String ADMIN_COMMISSION = "/commission";


    public static final String[] CLIENT ={PROPERTY_FOR_SALE_PATH, REQUEST_TO_BUY_PATH, DISTRICT_PATH, DEAL_PATH, LOGOUT,
            USERS, DISTRICT_PATH + "/delete"};
    public static final String[] ADMIN ={
            ADMIN_LOGOUT, ADMIN_REPORT, ADMIN_DEALS,
            ADMIN_PROPERTY_FOR_SALE, ADMIN_REQUEST_TO_BUY,
            ADMIN_TEST, ADMIN_DOWNLOAD_DEAL, ADMIN_COMMISSION
    };

    public static final String[] COMMON = {ADMIN_PATH, ROOT, REGISTER, ADMIN_PATH + ADMIN_LOGIN, LOGIN, "/favicon.ico"};
}

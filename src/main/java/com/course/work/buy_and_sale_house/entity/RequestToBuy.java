package com.course.work.buy_and_sale_house.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class RequestToBuy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private PropertyForSale saleChosenByUser;

    @ManyToMany
    private List<PropertyForSale> selectedSale;
    @ManyToOne
    private User user;
    private Double minArea;
    private Double maxArea;
    private boolean areaNoMatter;
    @ManyToMany
    private List<District> district;
    private boolean districtNoMatter;
    private int numberOfRooms;
    private boolean numberOfRoomsNoMatter;
    private Double minPrice;
    private Double maxPrice;
    private boolean priceNoMatter;
    private Date date;
    private String status;
    private String type;
    private String description = "Description must be here";
    private int countOfViews;

    private String fullDescription = "Full description is not added";

    public RequestToBuy() {

    }

    public List<PropertyForSale> getSelectedSale() {
        return selectedSale;
    }

    public void setSelectedSale(List<PropertyForSale> selectedSale) {
        this.selectedSale = selectedSale;
    }

    public void setMinArea(Double minArea) {
        this.minArea = minArea;
    }

    public void setMaxArea(Double maxArea) {
        this.maxArea = maxArea;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getMinArea() {
        return minArea;
    }

    public void setMinArea(double minArea) {
        this.minArea = minArea;
    }

    public Double getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(double maxArea) {
        this.maxArea = maxArea;
    }

    public boolean isAreaNoMatter() {
        return areaNoMatter;
    }

    public void setAreaNoMatter(boolean areaNoMatter) {
        this.areaNoMatter = areaNoMatter;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }

    public boolean isDistrictNoMatter() {
        return districtNoMatter;
    }

    public void setDistrictNoMatter(boolean districtNoMatter) {
        this.districtNoMatter = districtNoMatter;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public boolean isNumberOfRoomsNoMatter() {
        return numberOfRoomsNoMatter;
    }

    public void setNumberOfRoomsNoMatter(boolean numberOfRoomsNoMatter) {
        this.numberOfRoomsNoMatter = numberOfRoomsNoMatter;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isPriceNoMatter() {
        return priceNoMatter;
    }

    public void setPriceNoMatter(boolean priceNoMatter) {
        this.priceNoMatter = priceNoMatter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCountOfViews() {
        return countOfViews;
    }

    public void setCountOfViews(int countOfViews) {
        this.countOfViews = countOfViews;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public PropertyForSale getSaleChosenByUser() {
        return saleChosenByUser;
    }

    public void setSaleChosenByUser(PropertyForSale saleChosenByUser) {
        this.saleChosenByUser = saleChosenByUser;
    }
}

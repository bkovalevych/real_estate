package com.course.work.buy_and_sale_house.entity;

import com.course.work.buy_and_sale_house.service.PropertyForSaleService;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PropertyForSale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private District district;
    @ManyToOne
    private User user;
    @Transient
    private List<RequestToBuy> selectedRequestToBuy;
    @ManyToMany
    private List<RequestToBuy> candidates;
    private Double area;
    private Double price;
    private Integer numberOfRooms;
    private Date date;
    private String status;
    private String address;
    private String type;
    private String description = "Description must be here";
    private String fullDescription = "Full description is not added";
    private int countOfViews;

    public PropertyForSale(
            User user,
            District district,
            Double area,
            Double price,
            Integer numberOfRooms,
            String address,
            String type,
            String description,
            String fullDescription
    ) {
        this.user = user;
        this.district = district;
        this.area = area;
        this.price = price;
        this.numberOfRooms = numberOfRooms;
        this.address = address;
        this.type = type;
        this.description = description;
        this.fullDescription = fullDescription;
        this.status = PropertyForSaleService.STATUS_OK;
        this.selectedRequestToBuy = new ArrayList<>();
        this.date = new Date(new java.util.Date().getTime());
    }
    public PropertyForSale() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<RequestToBuy> getSelectedRequestToBuy() {
        return selectedRequestToBuy;
    }

    public void setSelectedRequestToBuy(List<RequestToBuy> selectedRequestToBuy) {
        this.selectedRequestToBuy = selectedRequestToBuy;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public List<RequestToBuy> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<RequestToBuy> candidates) {
        this.candidates = candidates;
    }
}

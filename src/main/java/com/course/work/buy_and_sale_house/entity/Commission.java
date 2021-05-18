package com.course.work.buy_and_sale_house.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Commission {
    public Commission() {

    }

    public Commission(double commission) {
        this.commission = commission;
        this.isRealFrom = new Date(new java.util.Date().getTime());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double commission;
    private Date isRealFrom;
    @OneToMany
    private List<Deal> deal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public Date getIsRealFrom() {
        return isRealFrom;
    }

    public void setIsRealFrom(Date isRealFrom) {
        this.isRealFrom = isRealFrom;
    }

    public List<Deal> getDeal() {
        return deal;
    }

    public void setDeal(List<Deal> deal) {
        this.deal = deal;
    }
}

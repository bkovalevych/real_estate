package com.course.work.buy_and_sale_house.entity;



import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Deal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private PropertyForSale propertyForSale;
    @OneToOne
    private RequestToBuy requestToBuy;
    private Date dateOfDeal;
    private String status;


    private Double commission;
    @ManyToOne
    private User seller;
    @ManyToOne
    private User buyer;
    private boolean acceptedByBuyer;
    private boolean acceptedBySeller;

    public boolean isAcceptedByBuyer() {
        return acceptedByBuyer;
    }

    public void setAcceptedByBuyer(boolean acceptedByBuyer) {
        this.acceptedByBuyer = acceptedByBuyer;
    }

    public boolean isAcceptedBySeller() {
        return acceptedBySeller;
    }

    public void setAcceptedBySeller(boolean acceptedBySeller) {
        this.acceptedBySeller = acceptedBySeller;
    }

    public Deal() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropertyForSale getPropertyForSale() {
        return propertyForSale;
    }

    public void setPropertyForSale(PropertyForSale propertyForSale) {
        this.propertyForSale = propertyForSale;
    }

    public RequestToBuy getRequestToBuy() {
        return requestToBuy;
    }

    public void setRequestToBuy(RequestToBuy requestToBuy) {
        this.requestToBuy = requestToBuy;
    }

    public Date getDateOfDeal() {
        return dateOfDeal;
    }

    public void setDateOfDeal(Date dateOfDeal) {
        this.dateOfDeal = dateOfDeal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}

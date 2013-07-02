package com.smobs.models;

import java.util.Date;

public class UserTrans {
    private double transAmount;
    private Date transDate;
    private String transType;
    private String category;
    private String description;

    public UserTrans(double transAmount, Date transDate, String transType, String category, String description) {
        this.transAmount = transAmount;
        this.transDate = transDate;
        this.transType = transType;
        this.category = category;
        this.description = description;
    }

    public double getTransAmount() {
        return transAmount;
    }

    public Date getTransDate() {
        return transDate;
    }

    public String getTransType() {
        return transType;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}

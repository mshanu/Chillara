package com.smobs.models;

public class User {
    private Long id;
    private String userName;
    private String imageId;
    private Double totalDebit;
    private Double totalCredit;

    public void setTotalDebit(Double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public void setTotalCredit(Double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Double getTotalDebit() {
        return totalDebit;
    }

    public Double getTotalCredit() {
        return totalCredit;
    }

    public User(String userName, String imageId) {
        this.userName = userName;
        this.imageId = imageId;
    }

    public User(Long id, String userName, String imageId, Double totalCredit, Double totalDebit) {
        this.id = id;
        this.userName = userName;
        this.imageId = imageId;
        this.totalDebit = totalDebit;
        this.totalCredit = totalCredit;
    }

    @Override
    public String toString() {
        return userName;
    }

    public String getUserHash() {
        return String.valueOf(userName.hashCode());
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateTrans(UserTrans userTrans) {
        if(userTrans.getTransType().equals("Credit")) {
            totalCredit += userTrans.getTransAmount();
        }else {
            totalDebit += userTrans.getTransAmount();
        }
    }
}

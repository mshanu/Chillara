package com.smobs.models;

public class Trans {
    private String userNameHash;
    private String userName;
    private Double totalCredit;
    private Double totalDebit;

    public void setUserNameHash(String userNameHash) {
        this.userNameHash = userNameHash;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTotalCredit(Double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public void setTotalDebit(Double totalDebit) {
        this.totalDebit = totalDebit;
    }
}

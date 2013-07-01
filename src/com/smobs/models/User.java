package com.smobs.models;

import java.util.List;

public class User {
    private String userName;
    private String imageId;
    private List<UserTrans> userTransList;

    public User(String userName, String imageUri) {
        this.userName = userName;
        this.imageId = imageUri;
    }

    @Override
    public String toString() {
        return userName;
    }
}

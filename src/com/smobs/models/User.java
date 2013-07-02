package com.smobs.models;

public class User {
    private Long id;
    private String userName;
    private String imageId;

    public User(String userName, String imageId) {
        this.userName = userName;
        this.imageId = imageId;
    }

    public User(Long id, String userName, String imageId) {
        this.id = id;
        this.userName = userName;
        this.imageId = imageId;
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
}

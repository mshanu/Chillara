package com.smobs.models;

import android.net.Uri;

public class User {
    private String userName;
    private String phoneNumber;
    private Uri imageUri;



    public User(String userName, String phoneNumber, Uri imageUri) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.imageUri = imageUri;
    }


}

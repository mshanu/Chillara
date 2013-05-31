package com.smobs.models;

import java.text.SimpleDateFormat;

public class MyDateFormat {
    private static SimpleDateFormat myDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static SimpleDateFormat getMyDateFormat() {
        return myDateFormat;
    }
}

package com.smobs.models;

import android.provider.BaseColumns;

public class TransReaderContract {

    public static final String create_table = "CREATE TABLE";
    public static final String space = " ";
    public static final String left_bracket = "(";
    public static final String right_bracket = ")";
    public static final String semicolon = ";";

    public class UserTrans implements BaseColumns {

        public static final String TABLE_NAME = "user_transactions";
        public static final String TRANS_USER_ID = "user_id";
        public static final String TRANS_TYPE = "trans_type";
        public static final String TRANS_CATEGORY_ID = "trans_category_id";
        public static final String TRANS_DESCRIPTION = "trans_description";
        public static final String TRANS_DATE = "trans_date";
        public static final String TRANS_AMOUNT = "trans_amount";
        public static final String REMINDER_DATE_TIME = "reminder_date_time";
    }

    public static String createStatementForUserTrans() {
        return new StringBuilder("create table ").append(UserTrans.TABLE_NAME).append("(").
                append(UserTrans._ID).append(" INTEGER PRIMARY KEY,").
                append(UserTrans.TRANS_USER_ID).append(" INTEGER,").
                append(UserTrans.TRANS_TYPE).append(" TEXT,").
                append(UserTrans.TRANS_CATEGORY_ID).append(" INTEGER,").
                append(UserTrans.TRANS_DESCRIPTION).append(" TEXT,").
                append(UserTrans.TRANS_DATE).append(" TEXT,").
                append(UserTrans.TRANS_AMOUNT).append(" REAL,").
                append(UserTrans.REMINDER_DATE_TIME).append(" TEXT);").toString();
    }

    public class User implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String TRANS_USER_HASH = "user_name_hash";
        public static final String TRANS_USER_NAME = "user_name";
        public static final String TRANS_USER_IMAGE_URL = "user_image_url";
    }

    public class TransCategory implements BaseColumns {
        public static final String TABLE_NAME = "transaction_category";
        public static final String DESCRIPTION = "description";
    }

    public static String createStatementForTransCategory() {
        return new StringBuilder("create table ").append(TransCategory.TABLE_NAME).
                append(TransCategory._ID).append(" INTEGER PRIMARY KEY").
                append(TransCategory.DESCRIPTION).append(" TEXT").toString();
    }

    public static String createStatementForUser() {

        return new StringBuilder("create table").append(User.TABLE_NAME).append("(").
                append(User._ID).append(" INTEGER PRIMARY KEY").
                append(User.TRANS_USER_HASH).append(" TEXT ").
                append(User.TRANS_USER_NAME).append(" TEXT").
                append(User.TRANS_USER_IMAGE_URL).append(" TEXT);").toString();

    }

    private TransReaderContract() {

    }


}

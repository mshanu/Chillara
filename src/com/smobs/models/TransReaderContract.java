package com.smobs.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class TransReaderContract {

    public static final String create_table = "CREATE TABLE";
    public static final String space = " ";
    public static final String left_bracket = "(";
    public static final String right_bracket = ")";
    public static final String semicolon = ";";

    public static void insertTrans(SQLiteDatabase writableDatabase, com.smobs.models.User searchedUser, com.smobs.models.UserTrans userTrans) {
        ContentValues values = new ContentValues();
        values.put(UserTrans.TRANS_AMOUNT, userTrans.getTransAmount());
        values.put(UserTrans.TRANS_CATEGORY, userTrans.getCategory());
        values.put(UserTrans.TRANS_DATE, MyDateFormat.getMyDateFormat().format(userTrans.getTransDate()));
        values.put(UserTrans.TRANS_TYPE, userTrans.getCategory());
        values.put(UserTrans.TRANS_USER_ID, searchedUser.getId());
        writableDatabase.insert(UserTrans.TABLE_NAME, null, values);
    }

    public class User implements BaseColumns {

        public static final String TABLE_NAME = "user";

        public static final String TRANS_USER_HASH = "user_name_hash";
        public static final String TRANS_USER_NAME = "user_name";
        public static final String TRANS_USER_IMAGE_ID = "user_image_url";
    }

    public class UserTrans implements BaseColumns {

        public static final String TABLE_NAME = "user_transactions";
        public static final String TRANS_USER_ID = "user_id";
        public static final String TRANS_TYPE = "trans_type";
        public static final String TRANS_CATEGORY = "trans_category";
        public static final String TRANS_DESCRIPTION = "trans_description";
        public static final String TRANS_DATE = "trans_date";
        public static final String TRANS_AMOUNT = "trans_amount";
    }

    public class TransCategory implements BaseColumns {

        public static final String TABLE_NAME = "transaction_category";
        public static final String DESCRIPTION = "description";
    }

    public static String createStatementForTransCategory() {
        return new StringBuilder("create table ").append(TransCategory.TABLE_NAME).append("(").
                append(TransCategory._ID).append(" INTEGER PRIMARY KEY,").
                append(TransCategory.DESCRIPTION).append(" TEXT);").toString();
    }

    public static String createStatementForUserTrans() {
        return new StringBuilder("create table ").append(UserTrans.TABLE_NAME).append("(").
                append(UserTrans._ID).append(" INTEGER PRIMARY KEY,").
                append(UserTrans.TRANS_USER_ID).append(" INTEGER,").
                append(UserTrans.TRANS_TYPE).append(" TEXT,").
                append(UserTrans.TRANS_CATEGORY).append(" TEXT,").
                append(UserTrans.TRANS_DESCRIPTION).append(" TEXT,").
                append(UserTrans.TRANS_DATE).append(" TEXT,").
                append(UserTrans.TRANS_AMOUNT).append(" REAL").toString();
    }

    public static String createStatementForUser() {

        return new StringBuilder("create table ").append(User.TABLE_NAME).append("(").
                append(User._ID).append(" INTEGER PRIMARY KEY,").
                append(User.TRANS_USER_HASH).append(" TEXT ,").
                append(User.TRANS_USER_NAME).append(" TEXT,").
                append(User.TRANS_USER_IMAGE_ID).append(" TEXT);").toString();

    }

    public static com.smobs.models.User findUserByHash(SQLiteDatabase db, String userNameHash) {
        Cursor userSearchCursor = db.query(User.TABLE_NAME, null, User.TRANS_USER_HASH + "=?", new String[]{userNameHash}, null, null, null);
        if (userSearchCursor.moveToNext()) {
            return new com.smobs.models.User(userSearchCursor.getLong(userSearchCursor.getColumnIndex(User._ID)),
                    userSearchCursor.getString(userSearchCursor.getColumnIndex(User.TRANS_USER_NAME)),
                    userSearchCursor.getString(userSearchCursor.getColumnIndex(User.TRANS_USER_IMAGE_ID)));
        }
        return null;
    }

    public static com.smobs.models.User insertUser(SQLiteDatabase writableDatabase, com.smobs.models.User selectedPerson) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.TRANS_USER_NAME, selectedPerson.getUserName());
        contentValues.put(User.TRANS_USER_HASH, selectedPerson.getUserHash());
        contentValues.put(User.TRANS_USER_IMAGE_ID, selectedPerson.getImageId());
        selectedPerson.setId(writableDatabase.insert(User.TABLE_NAME, null, contentValues));
        return selectedPerson;
    }

    private TransReaderContract() {

    }


}

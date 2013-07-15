package com.smobs.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

public class TransReaderContract {

    public static void insertTrans(SQLiteDatabase writableDatabase, com.smobs.models.User searchedUser, com.smobs.models.UserTrans userTrans) {
        ContentValues values = new ContentValues();
        values.put(UserTrans.TRANS_AMOUNT, userTrans.getTransAmount());
        values.put(UserTrans.TRANS_CATEGORY, userTrans.getCategory());
        values.put(UserTrans.TRANS_DATE, MyDateFormat.getMyDateFormat().format(userTrans.getTransDate()));
        values.put(UserTrans.TRANS_TYPE, userTrans.getTransType());
        values.put(UserTrans.TRANS_USER_ID, searchedUser.getId());
        values.put(UserTrans.TRANS_DESCRIPTION, userTrans.getDescription());
        writableDatabase.insert(UserTrans.TABLE_NAME, null, values);
        ContentValues userUpdate = new ContentValues();
        userUpdate.put(User.TRANS_USER_TOTAL_CREDIT, searchedUser.getTotalCredit());
        userUpdate.put(User.TRANS_USER_TOTAL_DEBIT, searchedUser.getTotalDebit());
        writableDatabase.update(User.TABLE_NAME, userUpdate, User._ID + "=?", new String[]{searchedUser.getId().toString()});
    }

    public static void updateCategory(SQLiteDatabase db, Long categoryId, Double amount, String type) {
        String categoryTypeColumn;
        if (type.toUpperCase().equals("CREDIT")) {
            categoryTypeColumn = TransCategory.TOTAL_CREDIT;
        } else {
            categoryTypeColumn = TransCategory.TOTAL_DEBIT;
        }
        String updateStatement = "select (" + categoryTypeColumn + "+" + amount + ") from " + TransCategory.TABLE_NAME + " where " + TransCategory._ID + "=" + categoryId;
        db.execSQL("update " + TransCategory.TABLE_NAME + " set " + categoryTypeColumn + " = (" + updateStatement + ") where " + TransCategory._ID + "=" + categoryId);
    }

    public class User implements BaseColumns {

        public static final String TABLE_NAME = "user";

        public static final String TRANS_USER_HASH = "user_name_hash";
        public static final String TRANS_USER_NAME = "user_name";
        public static final String TRANS_USER_IMAGE_ID = "user_image_url";
        public static final String TRANS_USER_TOTAL_CREDIT = "total_credit";
        public static final String TRANS_USER_TOTAL_DEBIT = "total_debit";
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
        public static final String TOTAL_CREDIT = "total_credit";
        public static final String TOTAL_DEBIT = "total_debit";
    }

    public static String createStatementForTransCategory() {
        return new StringBuilder("create table ").append(TransCategory.TABLE_NAME).append("(").
                append(TransCategory._ID).append(" INTEGER PRIMARY KEY,").
                append(TransCategory.TOTAL_CREDIT).append(" REAL DEFAULT 0,").
                append(TransCategory.TOTAL_DEBIT).append(" REAL DEFAULT 0,").
                append(TransCategory.DESCRIPTION).append(" TEXT NOT NULL, UNIQUE(" + TransCategory.DESCRIPTION + "));").toString();
    }

    public static String createStatementForUserTrans() {
        return new StringBuilder("create table ").append(UserTrans.TABLE_NAME).append("(").
                append(UserTrans._ID).append(" INTEGER PRIMARY KEY,").
                append(UserTrans.TRANS_USER_ID).append(" INTEGER,").
                append(UserTrans.TRANS_TYPE).append(" TEXT,").
                append(UserTrans.TRANS_CATEGORY).append(" TEXT,").
                append(UserTrans.TRANS_DESCRIPTION).append(" TEXT,").
                append(UserTrans.TRANS_DATE).append(" TEXT,").
                append(UserTrans.TRANS_AMOUNT).append(" REAL);").toString();
    }

    public static String createStatementForUser() {

        return new StringBuilder("create table ").append(User.TABLE_NAME).append("(").
                append(User._ID).append(" INTEGER PRIMARY KEY,").
                append(User.TRANS_USER_HASH).append(" TEXT ,").
                append(User.TRANS_USER_NAME).append(" TEXT,").
                append(User.TRANS_USER_IMAGE_ID).append(" TEXT,").
                append(User.TRANS_USER_TOTAL_CREDIT).append(" REAL,").
                append(User.TRANS_USER_TOTAL_DEBIT).append(" REAL);").toString();

    }

    public static com.smobs.models.User findUserByHash(SQLiteDatabase db, String userNameHash) {
        Cursor userSearchCursor = db.query(User.TABLE_NAME, null, User.TRANS_USER_HASH + "=?", new String[]{userNameHash}, null, null, null);
        if (userSearchCursor.moveToNext()) {
            return new com.smobs.models.User(userSearchCursor.getLong(userSearchCursor.getColumnIndex(User._ID)),
                    userSearchCursor.getString(userSearchCursor.getColumnIndex(User.TRANS_USER_NAME)),
                    userSearchCursor.getString(userSearchCursor.getColumnIndex(User.TRANS_USER_IMAGE_ID)),
                    userSearchCursor.getDouble(userSearchCursor.getColumnIndex(User.TRANS_USER_TOTAL_CREDIT)),
                    userSearchCursor.getDouble(userSearchCursor.getColumnIndex(User.TRANS_USER_TOTAL_DEBIT)));
        }
        return null;
    }


    public static Cursor getTransListByUser(TransDBHelper transDBHelper, String groupBy) {
        SQLiteDatabase readableDatabase = transDBHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TransReaderContract.User.TABLE_NAME + " JOIN " + TransReaderContract.UserTrans.TABLE_NAME
                + " ON " +
                TransReaderContract.User.TABLE_NAME + "." + TransReaderContract.User._ID + "=" + TransReaderContract.UserTrans.TABLE_NAME + "." + TransReaderContract.UserTrans.TRANS_USER_ID);
        String orderBy = TransReaderContract.UserTrans.TRANS_DATE + " desc";
        return queryBuilder.query(readableDatabase, null, null, null, groupBy, null, orderBy);
    }

    public static Cursor getTransCategory(TransDBHelper transDBHelper) {
        SQLiteDatabase readableDatabase = transDBHelper.getReadableDatabase();
        return readableDatabase.query(TransCategory.TABLE_NAME, null, null, null, null, null, null, null);
    }

    public static com.smobs.models.User insertUser(SQLiteDatabase writableDatabase, com.smobs.models.User selectedPerson) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.TRANS_USER_NAME, selectedPerson.getUserName());
        contentValues.put(User.TRANS_USER_HASH, selectedPerson.getUserHash());
        contentValues.put(User.TRANS_USER_IMAGE_ID, selectedPerson.getImageId());
        contentValues.put(User.TRANS_USER_TOTAL_CREDIT, selectedPerson.getTotalCredit());
        contentValues.put(User.TRANS_USER_TOTAL_DEBIT, selectedPerson.getTotalDebit());
        selectedPerson.setId(writableDatabase.insert(User.TABLE_NAME, null, contentValues));
        return selectedPerson;
    }

    private TransReaderContract() {

    }


}

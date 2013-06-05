package com.smobs.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransDBHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Trans.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TransReaderContract.TransEntry.TABLE_NAME +
                    " (" + TransReaderContract.TransEntry._ID + " INTEGER PRIMARY KEY,"
                    + TransReaderContract.TransEntry.TRANS_USER_ID + " INTEGER" + COMMA_SEP
                    + TransReaderContract.TransEntry.TRANS_TYPE + TEXT_TYPE + COMMA_SEP
                    + TransReaderContract.TransEntry.TRANS_DATE + TEXT_TYPE + COMMA_SEP
                    + TransReaderContract.TransEntry.TRANS_AMOUNT + " REAL);";
    private static final String SQL_CREATE_TRANS_USER = "CREATE TABLE " +
            TransReaderContract.TransUser.TABLE_NAME + "(" + TransReaderContract.TransUser._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            TransReaderContract.TransUser.TRANS_USER_HASH + " INTEGER" + COMMA_SEP +
            TransReaderContract.TransUser.TRANS_USER_NAME + " TEXT" + COMMA_SEP +
            TransReaderContract.TransUser.TRANS_USER_TOTAL_DEBIT + " REAL" + COMMA_SEP +
            TransReaderContract.TransUser.TRANS_USER_TOTAL_CREDIT + " REAL);";

    public TransDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_TRANS_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TransReaderContract.TransEntry.TABLE_NAME + "';");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TransReaderContract.TransUser.TABLE_NAME + "';");
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_TRANS_USER);
    }

}

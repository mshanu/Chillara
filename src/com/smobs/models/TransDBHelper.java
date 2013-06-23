package com.smobs.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Trans.db";


    public TransDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TransReaderContract.createStatementForTransCategory());
        sqLiteDatabase.execSQL(TransReaderContract.createStatementForUser());
        sqLiteDatabase.execSQL(TransReaderContract.createStatementForUserTrans());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TransReaderContract.UserTrans.TABLE_NAME + "';");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TransReaderContract.User.TABLE_NAME + "';");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TransReaderContract.TransCategory.TABLE_NAME + "';");
    }

}

package com.smobs.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TransDBHelper extends SQLiteOpenHelper{
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Trans.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TransReaderContract.TransEntry.TABLE_NAME +
                    " (" + TransReaderContract.TransEntry._ID + " INTEGER PRIMARY KEY,"
                    +TransReaderContract.TransEntry.PERSON_NAME_HASH + " INTEGER" + COMMA_SEP
                    +TransReaderContract.TransEntry.PERSON_NAME + TEXT_TYPE + COMMA_SEP
                    +TransReaderContract.TransEntry.TRANS_TYPE + TEXT_TYPE + COMMA_SEP
                    +TransReaderContract.TransEntry.TRANS_DATE + TEXT_TYPE + COMMA_SEP
                    +TransReaderContract.TransEntry.TRANS_AMOUNT + " REAL);";

    public TransDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    
}

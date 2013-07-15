package com.smobs.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.smobs.models.TransReaderContract.createStatementForTransCategory;
import static com.smobs.models.TransReaderContract.createStatementForUser;
import static com.smobs.models.TransReaderContract.createStatementForUserTrans;

public class TransDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "Trans.db";


    public TransDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createStatementForTransCategory());
        sqLiteDatabase.execSQL(createStatementForUser());
        sqLiteDatabase.execSQL(createStatementForUserTrans());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TransReaderContract.UserTrans.TABLE_NAME + "';");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TransReaderContract.User.TABLE_NAME + "';");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TransReaderContract.TransCategory.TABLE_NAME + "';");

        sqLiteDatabase.execSQL(createStatementForTransCategory());
        sqLiteDatabase.execSQL(createStatementForUser());
        sqLiteDatabase.execSQL(createStatementForUserTrans());
    }

    public void addNewCategory(String category) {
        String condition = TransReaderContract.TransCategory.DESCRIPTION + "=?";
        Cursor cursor = getReadableDatabase().query(TransReaderContract.TransCategory.TABLE_NAME, null, condition, new String[]{category}, null, null, null);
        if (cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TransReaderContract.TransCategory.DESCRIPTION, category.toUpperCase());
            getWritableDatabase().insert(TransReaderContract.TransCategory.TABLE_NAME, null, contentValues);
        }
        close();
    }

}

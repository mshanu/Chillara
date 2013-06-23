package com.smobs.chillara;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;


public class HasTransList extends Activity {


    protected Cursor getTransListBy(TransDBHelper transDBHelper, String groupBy) {
        SQLiteDatabase readableDatabase = transDBHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TransReaderContract.User.TABLE_NAME + " JOIN " + TransReaderContract.UserTrans.TABLE_NAME
                + " ON " +
                TransReaderContract.User.TABLE_NAME + "." + TransReaderContract.User._ID + "=" + TransReaderContract.UserTrans.TABLE_NAME + "." + TransReaderContract.UserTrans.TRANS_USER_ID);
        String orderBy = TransReaderContract.UserTrans.TRANS_DATE + " desc";
        return queryBuilder.query(readableDatabase, null, null, null, groupBy, null, orderBy);
    }
}

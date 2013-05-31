package com.smobs.chillara;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;

public class MainActivity extends Activity {

    private MenuItem addTrans;
    private TransDBHelper transDBHelper;
    private SimpleCursorAdapter simpleCursorAdapter;
    private SQLiteDatabase readableDatabase;
    private ListView trans_list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        transDBHelper = new TransDBHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trans_list_view = (ListView) findViewById(R.id.trans_list);
        transToListView();


    }

    private void transToListView() {
        readableDatabase = transDBHelper.getReadableDatabase();
        Cursor query = readableDatabase.query(true,
                TransReaderContract.TransEntry.TABLE_NAME,
                TransReaderContract.allColumns(), null, null, null, null, null,
                null);
        if (query.getCount() > 0) {
            String[] columns = new String[]{
                    TransReaderContract.TransEntry.PERSON_NAME,
                    TransReaderContract.TransEntry.TRANS_AMOUNT,
                    TransReaderContract.TransEntry.TRANS_TYPE,
                    TransReaderContract.TransEntry.TRANS_DATE};
            int[] to = new int[]{R.id.trans_list_name,
                    R.id.trans_list_amount, R.id.trans_list_type, R.id.trans_list_date};
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.trans_list_item, query, columns, to, 0);
            trans_list_view.setAdapter(simpleCursorAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        //return super.onCreatePanelMenu(featureId, menu);
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_trans_icon) {
            navigateToAddTrans();
        }
        return super.onOptionsItemSelected(item);
    }


    private void navigateToAddTrans() {
        Intent intent = new Intent(this, AddTransActivity.class);
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        transToListView();
    }
}

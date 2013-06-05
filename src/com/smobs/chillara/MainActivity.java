package com.smobs.chillara;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.ToggleButton;
import com.smobs.chillara.adapter.TransCursorAdapter;
import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;

public class MainActivity extends Activity {


    private TransDBHelper transDBHelper;
    private TransCursorAdapter simpleCursorAdapter;
    private SQLiteDatabase readableDatabase;
    private ListView transListView;
    private ToggleButton all;
    private ToggleButton debit;
    private ToggleButton credit;
    private View transSummary;
    private ToggleButton summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //By this, we will have a title bar that works for all versions :-)
        transDBHelper = new TransDBHelper(getApplicationContext());
        setContentView(R.layout.activity_main);
        transListView = (ListView) findViewById(R.id.trans_list);
        transSummary = findViewById(R.id.trans_summary);

        all = (ToggleButton) findViewById(R.id.btn_all);
        debit = (ToggleButton) findViewById(R.id.btn_debit);
        credit = (ToggleButton) findViewById(R.id.btn_credit);
        summary = (ToggleButton) findViewById(R.id.btn_summary);


        showDefaultList();


    }

    private void showDefaultList() {
        all.setChecked(true);
        transToListView("%");
    }

    private void transToListView(String queryTransType) {
        readableDatabase = transDBHelper.getReadableDatabase();
        Cursor query = readableDatabase.query(true,
                TransReaderContract.TransEntry.TABLE_NAME,
                TransReaderContract.allTransEntryColumns(), TransReaderContract.TransEntry.TRANS_TYPE + " like ?", new String[]{queryTransType}, null, null, null,
                null);
        if (query.getCount() > 0) {
            String[] columns = new String[]{
                    TransReaderContract.TransEntry.TRANS_USER_ID,
                    TransReaderContract.TransEntry.TRANS_AMOUNT,
                    TransReaderContract.TransEntry.TRANS_TYPE,
                    TransReaderContract.TransEntry.TRANS_DATE};
            int[] to = new int[]{R.id.trans_list_name,
                    R.id.trans_list_amount, R.id.trans_list_type, R.id.trans_list_date};
            simpleCursorAdapter = new TransCursorAdapter(this,
                    R.layout.trans_list_item, query, columns, to);
            transListView.setAdapter(simpleCursorAdapter);

            transListView.setVisibility(View.VISIBLE);
            transSummary.setVisibility(View.INVISIBLE);
        }
    }

    public void navigateToAddTrans(View view) {
        Intent intent = new Intent(this, AddTransActivity.class);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showDefaultList();
    }

    public void onToggle(View view) {

        all.setChecked(false);
        debit.setChecked(false);
        credit.setChecked(false);
        summary.setChecked(false);

        ToggleButton selectedButton = (ToggleButton) view;
        selectedButton.setChecked(true);
        int selectedId = selectedButton.getId();
        switch (selectedId) {
            case R.id.btn_all:
                transToListView("%");
                break;
            case R.id.btn_credit:
                transToListView("Credit");
                break;
            case R.id.btn_debit:
                transToListView("Debit");
                break;
            case R.id.btn_summary:
                transListView.setVisibility(View.INVISIBLE);
                transSummary.setVisibility(View.VISIBLE);

        }
    }
}

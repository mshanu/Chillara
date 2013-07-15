package com.smobs.chillara;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;

public class TransUserListActivity extends Activity {
    private ListView transListView;
    private TextView transUserView;
    private TextView totalCrediText;
    private TextView totalDebitText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.trans_list_by_user_layout);

        transUserView = (TextView) findViewById(R.id.trans_details_user_name);
        totalCrediText = (TextView) findViewById(R.id.total_credit_value);
        totalDebitText = (TextView) findViewById(R.id.total_debit_amount);

        transUserView.setText(getIntent().getExtras().getString("TRANS_HEADER_TYPE"));
        totalCrediText.setText(getIntent().getExtras().getString("TRAN_HEADER_TOTAL_CREDIT"));
        totalDebitText.setText(getIntent().getExtras().getString("TRAN_HEADER_TOTAL_DEBIT"));

        String[] from = getIntent().getExtras().getStringArray("TRANS_LIST_FROM");
        int[] to = getIntent().getExtras().getIntArray("TRANS_LIST_TO");

        transListView = (ListView) findViewById(R.id.trans_list_for_user_view);
        Cursor cursor = TransReaderContract.getTransListByUser(new TransDBHelper(this), null);

        if (cursor.getCount() > 0) {
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.trans_detail_content_item, cursor, from, to);
            transListView.setAdapter(simpleCursorAdapter);
        }
    }

}
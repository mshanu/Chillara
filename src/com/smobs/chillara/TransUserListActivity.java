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

        transUserView.setText(getIntent().getExtras().getString("TRANS_USER_NAME"));
        totalCrediText.setText(getIntent().getExtras().getString("TRANS_USER_TOTAL_CREDIT"));
        totalDebitText.setText(getIntent().getExtras().getString("TRANS_USER_TOTAL_DEBIT"));

        String[] from = new String[]{
                TransReaderContract.User.TRANS_USER_NAME,
                TransReaderContract.UserTrans.TRANS_AMOUNT,
                TransReaderContract.UserTrans.TRANS_DATE,
                TransReaderContract.UserTrans.TRANS_TYPE
        };
        int[] to = new int[]{
                R.id.trans_list_item_name,
                R.id.trans_list_item_amount,
                R.id.trans_list_item_date,
                R.id.trans_list_item_type};
        transListView = (ListView) findViewById(R.id.trans_list_view);
        Cursor cursor = new HasTransList().getTransListBy(new TransDBHelper(this),null);

        if (cursor.getCount() > 0) {
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.trans_list_item, cursor, from, to);
            transListView.setAdapter(simpleCursorAdapter);
        }
    }

}
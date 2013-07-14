package com.smobs.chillara;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;

public class MainActivity extends Activity {

    private ListView transList;
    private Button subMenuAll;
    private Button subMenuCategory;
    private Button subMenuType;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //By this, we will have a title bar that works for all versions :-)
        setContentView(R.layout.activity_main);
        transList = (ListView) findViewById(R.id.layout_translist);

        subMenuAll = (Button) findViewById(R.id.main_submenu_all);
        subMenuAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleColor((Button) view);
                transToListView();
            }
        });


        subMenuCategory = (Button) findViewById(R.id.main_submenu_category);
        subMenuCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleColor((Button) view);
                subMenuCategory.setTextColor(getResources().getColor(R.color.green));
                String[] from = new String[]{
                        TransReaderContract.TransCategory.DESCRIPTION,
                        TransReaderContract.TransCategory.TOTAL_CREDIT,
                        TransReaderContract.TransCategory.TOTAL_DEBIT
                };
                int[] to = new int[]{R.id.trans_by_user_name,
                        R.id.trans_list_credit, R.id.trans_list_debit};
                Cursor transCategoryCursor = TransReaderContract.getTransCategory(new TransDBHelper(view.getContext()));
                if (transCategoryCursor.getCount() > 0) {
                    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(view.getContext(),
                            R.layout.trans_by_user_item, transCategoryCursor, from, to);
                    transList.setOnItemClickListener(null);
                    transList.setAdapter(simpleCursorAdapter);
                }
            }

        });

        subMenuType = (Button) findViewById(R.id.main_submenu_type);

        transToListView();


    }

    private void toggleColor(Button pressedButton) {
        subMenuAll.setTextColor(getResources().getColor(R.color.grey));
        subMenuCategory.setTextColor(getResources().getColor(R.color.grey));
        pressedButton.setTextColor(getResources().getColor(R.color.green));
    }

    private void transToListView() {
        String[] from = new String[]{
                TransReaderContract.User.TRANS_USER_NAME,
                TransReaderContract.User.TRANS_USER_TOTAL_CREDIT,
                TransReaderContract.User.TRANS_USER_TOTAL_DEBIT};
        int[] to = new int[]{R.id.trans_by_user_name,
                R.id.trans_list_credit, R.id.trans_list_debit};
        final Cursor cursor = TransReaderContract.getTransListByUser(new TransDBHelper(this), TransReaderContract.UserTrans.TRANS_USER_ID);
        if (cursor.getCount() > 0) {
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.trans_by_user_item, cursor, from, to);
            transList.setAdapter(simpleCursorAdapter);
            transList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    cursor.moveToPosition(i);
                    Intent intent = new Intent(view.getContext(), TransUserListActivity.class);
                    intent.putExtra("TRANS_USER_NAME", cursor.getString(cursor.getColumnIndex(TransReaderContract.User.TRANS_USER_NAME)));
                    intent.putExtra("TRANS_USER_TOTAL_CREDIT", cursor.getString(cursor.getColumnIndex(TransReaderContract.User.TRANS_USER_TOTAL_CREDIT)));
                    intent.putExtra("TRANS_USER_TOTAL_DEBIT", cursor.getString(cursor.getColumnIndex(TransReaderContract.User.TRANS_USER_TOTAL_DEBIT)));
                    startActivity(intent);
                }
            });
        }
    }

    public void navigateToAddTrans(View view) {
        Intent intent = new Intent(this, AddTransActivity.class);
        startActivityForResult(intent, 10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}

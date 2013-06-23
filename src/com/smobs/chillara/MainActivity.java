package com.smobs.chillara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //By this, we will have a title bar that works for all versions :-)
        setContentView(R.layout.activity_main);

        //transToListView();


    }


//    private void transToListView() {
//        String[] from = new String[]{
//                TransReaderContract.User.TRANS_USER_NAME,
//                TransReaderContract.User.TRANS_USER_TOTAL_CREDIT,
//                TransReaderContract.User.TRANS_USER_TOTAL_DEBIT};
//        int[] to = new int[]{R.id.trans_by_user_name,
//                R.id.trans_list_credit, R.id.trans_list_debit};
//        final Cursor cursor = new HasTransList().getTransListBy(new TransDBHelper(this), TransReaderContract.UserTrans.TRANS_USER_ID);
//        if (cursor.getCount() > 0) {
//            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
//                    R.layout.trans_by_user_item, cursor, from, to);
//            transListView.setAdapter(simpleCursorAdapter);
//            transListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    cursor.moveToPosition(i);
//                    Intent intent = new Intent(view.getContext(), TransUserListActivity.class);
//                    intent.putExtra("TRANS_USER_NAME", cursor.getString(cursor.getColumnIndex(TransReaderContract.User.TRANS_USER_NAME)));
//                    intent.putExtra("TRANS_USER_TOTAL_CREDIT", cursor.getString(cursor.getColumnIndex(TransReaderContract.User.TRANS_USER_TOTAL_CREDIT)));
//                    intent.putExtra("TRANS_USER_TOTAL_DEBIT", cursor.getString(cursor.getColumnIndex(TransReaderContract.User.TRANS_USER_TOTAL_DEBIT)));
//                    startActivity(intent);
//                }
//            });
//        }
//    }

    public void navigateToAddTrans(View view) {
        Intent intent = new Intent(this, AddTransActivity.class);
        startActivityForResult(intent, 10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}

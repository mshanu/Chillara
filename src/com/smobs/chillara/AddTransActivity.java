package com.smobs.chillara;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.smobs.chillara.fragment.AddCategoryDialog;
import com.smobs.chillara.fragment.DatePickerFragment;
import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;

import java.util.ArrayList;
import java.util.List;

public class AddTransActivity extends FragmentActivity implements OnItemClickListener, OnClickListener, AddCategoryDialog.AddCategoryReactions {

    private TextView searchedPerson;
    private TextView transAmount;
    private RadioGroup transType;
    private TransDBHelper transDBHelper;
    private Button transDate;
    private ArrayAdapter<String> searchContactAdapter;
    private AutoCompleteTextView searchContact;
    private String selectedPerson;
    private TextView transDescription;
    private View addCategoryButton;
    private SimpleCursorAdapter simpleCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //By this, we will have a title bar that works for all versions :-)
        setContentView(R    .layout.activity_add_trans);
        addCategoryButton = findViewById(R.id.add_trans_add_category);
        addCategoryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryDialog addCategoryDialog = new AddCategoryDialog();
                addCategoryDialog.show(getSupportFragmentManager(), "Dialog");
            }
        });

        transDBHelper = new TransDBHelper(this);
        Cursor cursor = getAllCategories(transDBHelper.getReadableDatabase());

        Spinner categorySpinner = (Spinner) findViewById(R.id.add_trans_category);

        simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, new String[]{TransReaderContract.TransCategory.DESCRIPTION}, new int[]{android.R.id.text1});
        simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(simpleCursorAdapter);

    }

    private Cursor getAllCategories(SQLiteDatabase readableDatabase) {
        return readableDatabase.query(TransReaderContract.TransCategory.TABLE_NAME,
                new String[]{TransReaderContract.TransCategory._ID, TransReaderContract.TransCategory.DESCRIPTION}, null, null, null, null, null);
    }


    @Override
    public void onSave() {
        simpleCursorAdapter.changeCursor(getAllCategories(transDBHelper.getReadableDatabase()));
    }

    @Override
    public void onCancel() {

    }


    private List<String> getContacts() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        List<String> contactNames = new ArrayList<String>();
        while (cursor.moveToNext()) {
            contactNames.add(cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME)));
        }
        return contactNames;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedPerson = searchContactAdapter.getItem(position);
    }

    public void onDatePickerButtonClick(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    @Override
    public void onClick(View v) {

//        if (selectedPerson == null) {
//            selectedPerson = searchContact.getText().toString();
//        }
//
//        if (selectedPerson.length() == 0 || transAmount.getText().length() == 0) {
//            new AlertDialog.Builder(this).setTitle("Please Correct").setMessage("Person or Amount Cannot Be Empty").setNeutralButton("OK", null).show();
//        } else {
//            SQLiteDatabase readableDatabase = transDBHelper.getReadableDatabase();
//            SQLiteDatabase writableDatabase = transDBHelper.getWritableDatabase();
//
//            Cursor userCursor = readableDatabase.query(true, TABLE_NAME, null,
//                    TransReaderContract.User.TRANS_USER_HASH + "= ?", new String[]{String.valueOf(selectedPerson.hashCode())},
//                    null, null, null, null);
//            long userTransId;
//            double totalUserCredit = 0.0;
//            double totalUserDebit = 0.0;
//            if (userCursor.getCount() == 0) {
//                ContentValues values = new ContentValues();
//                values.put(TRANS_USER_HASH, selectedPerson.hashCode());
//                values.put(TRANS_USER_NAME, selectedPerson);
//                values.put(TRANS_USER_TOTAL_CREDIT, "0.0");
//                values.put(TRANS_USER_TOTAL_DEBIT, "0.0");
//                userTransId = writableDatabase.insert(TransReaderContract.User.TABLE_NAME, null, values);
//
//            } else {
//                userCursor.moveToFirst();
//                userTransId = userCursor.getLong(userCursor.getColumnIndex(_ID));
//                totalUserCredit = userCursor.getDouble(userCursor.getColumnIndex(TRANS_USER_TOTAL_CREDIT));
//                totalUserDebit = userCursor.getDouble(userCursor.getColumnIndex(TRANS_USER_TOTAL_DEBIT));
//            }
//            ContentValues values = new ContentValues();
//            RadioButton selectedTransType = (RadioButton) findViewById(transType.getCheckedRadioButtonId());
//            String transType = selectedTransType.getText().toString();
//
//            values.put(TransReaderContract.UserTrans.TRANS_AMOUNT, transAmount.getText().toString());
//            values.put(TransReaderContract.UserTrans.TRANS_USER_ID, userTransId);
//            values.put(TransReaderContract.UserTrans.TRANS_TYPE, transType);
//            values.put(TransReaderContract.UserTrans.TRANS_DATE, transDate.getText().toString());
//            values.put(TransReaderContract.UserTrans.TRANS_DESCRIPTION, transDescription.getText().toString());
//            writableDatabase.insert(TransReaderContract.UserTrans.TABLE_NAME, null, values);
//
//            if (transType.equals("Credit")) {
//                totalUserCredit = totalUserCredit + Double.valueOf(transAmount.getText().toString());
//            } else {
//                totalUserDebit = totalUserDebit + Double.valueOf(transAmount.getText().toString());
//            }
//
//            String strFilter = _ID + "=?";
//            values = new ContentValues();
//            values.put(TRANS_USER_TOTAL_CREDIT, totalUserCredit);
//            values.put(TRANS_USER_TOTAL_DEBIT, totalUserDebit);
//            writableDatabase.update(TransReaderContract.User.TABLE_NAME, values, strFilter, new String[]{String.valueOf(userTransId)});
//            finish();
//        }


    }


}

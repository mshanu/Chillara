package com.smobs.chillara;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.smobs.chillara.fragment.DatePickerFragment;
import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.smobs.models.MyDateFormat.getMyDateFormat;
import static com.smobs.models.TransReaderContract.TransUser.*;

public class AddTransActivity extends FragmentActivity implements OnItemClickListener, OnClickListener {

    private TextView searchedPerson;
    private TextView transAmount;
    private RadioGroup transType;
    private TransDBHelper transDBHelper;
    private Button transDate;
    private ArrayAdapter<String> searchContactAdapter;
    private AutoCompleteTextView searchContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transDBHelper = new TransDBHelper(getApplicationContext());

        setContentView(R.layout.activity_add_trans);

        transType = (RadioGroup) findViewById(R.id.trans_type);
        searchedPerson = (TextView) findViewById(R.id.search_contact);

        transAmount = (TextView) findViewById(R.id.trans_amount);
        transDate = (Button) findViewById(R.id.trans_date);

        transDate.setText(getMyDateFormat().format(new Date()));

        searchContactAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getContacts());
        searchContact = (AutoCompleteTextView) findViewById(R.id.search_contact);
        searchContact.setOnItemClickListener(this);

        //Done as on selection of text is invisible after selection of the searched string.
        Resources resources = getResources();
        int black = resources.getColor(android.R.color.black);
        searchContact.setTextColor(black);


        Button saveButton = (Button) findViewById(R.id.trans_save);
        saveButton.setOnClickListener(this);
        searchContact.setAdapter(searchContactAdapter);

//        AccountManager accountManager = AccountManager.get(this);
//        Account[] googleAccountType = accountManager.getAccountsByType("com.google");


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

    }

    public void onDatePickerButtonClick(View view) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase readableDatabase = transDBHelper.getReadableDatabase();
        SQLiteDatabase writableDatabase = transDBHelper.getWritableDatabase();

        Cursor userCursor = readableDatabase.query(true, TABLE_NAME, null,
                "TransReaderContract.TransUser.TRANS_USER_HASH = ?", new String[]{String.valueOf(searchedPerson.getText().hashCode())},
                null, null, null, null);
        long userTransId;
        double totalUserCredit = 0.0;
        double totalUserDebit = 0.0;
        if (userCursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(TRANS_USER_HASH, searchedPerson.getText().hashCode());
            values.put(TRANS_USER_NAME, searchedPerson.getText().toString());
            values.put(TRANS_USER_TOTAL_CREDIT, totalUserCredit);
            values.put(TRANS_USER_TOTAL_DEBIT, totalUserDebit);
            userTransId = writableDatabase.insert(TransReaderContract.TransEntry.TABLE_NAME, null, values);

        } else {
            userCursor.moveToFirst();
            userTransId = userCursor.getLong(userCursor.getColumnIndex(_ID));
            totalUserCredit = userCursor.getDouble(userCursor.getColumnIndex(TRANS_USER_TOTAL_CREDIT));
            totalUserDebit = userCursor.getDouble(userCursor.getColumnIndex(TRANS_USER_TOTAL_DEBIT));
        }
        ContentValues values = new ContentValues();
        RadioButton selectedTransType = (RadioButton) findViewById(transType.getCheckedRadioButtonId());
        String transType = selectedTransType.getText().toString();

        values.put(TransReaderContract.TransEntry.TRANS_AMOUNT, transAmount.getText().toString());
        values.put(TransReaderContract.TransEntry.TRANS_USER_ID, userTransId);
        values.put(TransReaderContract.TransEntry.TRANS_TYPE, transType);
        values.put(TransReaderContract.TransEntry.TRANS_DATE, transDate.getText().toString());
        writableDatabase.insert(TransReaderContract.TransEntry.TABLE_NAME, null, values);

        if (transType.equals("Credit")) {
            totalUserCredit = totalUserCredit + Double.valueOf(transAmount.getText().toString());
        } else {
            totalUserDebit = totalUserDebit + Double.valueOf(transAmount.getText().toString());
        }

        String strFilter = _ID + "=?";
        values = new ContentValues();
        values.put(TRANS_USER_TOTAL_CREDIT, totalUserCredit);
        values.put(TRANS_USER_TOTAL_DEBIT, totalUserDebit);
        writableDatabase.update(TransReaderContract.TransUser.TABLE_NAME, values, strFilter, new String[]{String.valueOf(userTransId)});

        finish();
    }

}

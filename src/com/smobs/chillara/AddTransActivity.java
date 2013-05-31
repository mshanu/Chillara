package com.smobs.chillara;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.smobs.chillara.fragment.DatePickerFragment;
import com.smobs.models.MyDateFormat;
import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;

import static com.smobs.models.MyDateFormat.getMyDateFormat;

public class AddTransActivity extends FragmentActivity implements OnItemClickListener, OnClickListener {

    private TextView searchedPerson;
    private TextView transAmount;
    private RadioGroup transType;
    private TransDBHelper transDBHelper;
    private Button transDate;


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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getContacts());
        AutoCompleteTextView searchContact = (AutoCompleteTextView) findViewById(R.id.search_contact);
        searchContact.setOnItemClickListener(this);
        Button saveButton = (Button) findViewById(R.id.trans_save);
        saveButton.setOnClickListener(this);
        searchContact.setAdapter(adapter);

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
        SQLiteDatabase writableDatabase = transDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TransReaderContract.TransEntry.PERSON_NAME_HASH, searchedPerson.getText().hashCode());
        values.put(TransReaderContract.TransEntry.PERSON_NAME, searchedPerson.getText().toString());
        values.put(TransReaderContract.TransEntry.TRANS_AMOUNT, transAmount.getText().toString());
        RadioButton selectedTransType = (RadioButton) findViewById(transType.getCheckedRadioButtonId());
        CharSequence text = selectedTransType.getText();
        values.put(TransReaderContract.TransEntry.TRANS_TYPE, text.toString());
        values.put(TransReaderContract.TransEntry.TRANS_DATE, transDate.getText().toString());
        writableDatabase.insert(TransReaderContract.TransEntry.TABLE_NAME, null, values);

        finish();
    }

}

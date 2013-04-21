package com.smobs.chillara;

import java.sql.Date;
import java.util.ArrayList;
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

import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;

public class AddTransActivity extends Activity implements OnItemClickListener,OnClickListener {
	
	private TextView searchedPerson;
	private DatePicker transDate;
	private TextView transAmount;
	private RadioGroup transType;
	private TransDBHelper transDBHelper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		transDBHelper = new TransDBHelper(getApplicationContext());
		
		setContentView(R.layout.activity_add_trans);
		
		transType = (RadioGroup) findViewById(R.id.trans_type);
		searchedPerson = (TextView)findViewById(R.id.search_contact);
		transDate = (DatePicker)findViewById(R.id.trans_date);
		transAmount = (TextView)findViewById(R.id.trans_amount);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getContacts());
		AutoCompleteTextView searchContact = (AutoCompleteTextView) findViewById(R.id.search_contact);
		searchContact.setOnItemClickListener(this);
		Button saveButton = (Button) findViewById(R.id.trans_save);
		saveButton.setOnClickListener(this);
		searchContact.setAdapter(adapter);
		
	}
	
	private List<String> getContacts(){
		ContentResolver contentResolver = getContentResolver();
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = null;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;
		Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
		List<String> contactNames = new ArrayList<String>();
		while(cursor.moveToNext()){
			contactNames.add(cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME)));
		}
		return contactNames;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_trans, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}

	@Override
	public void onClick(View v) {
		SQLiteDatabase writableDatabase = transDBHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TransReaderContract.TransEntry.PERSON_NAME_HASH,searchedPerson.getText().hashCode());
		values.put(TransReaderContract.TransEntry.PERSON_NAME,searchedPerson.getText().toString());
		values.put(TransReaderContract.TransEntry.TRANS_AMOUNT,transAmount.getText().toString());
		RadioButton selectedTransType = (RadioButton) findViewById(transType.getCheckedRadioButtonId());
		CharSequence text = selectedTransType.getText();
		values.put(TransReaderContract.TransEntry.TRANS_TYPE,text.toString());
		values.put(TransReaderContract.TransEntry.TRANS_DATE,new Date(transDate.getYear() - 1900, transDate.getMonth(), transDate.getDayOfMonth()).toString());
		
		writableDatabase.insert(TransReaderContract.TransEntry.TABLE_NAME, null, values);
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
	}
	
	
}

package com.smobs.chillara;

import com.smobs.models.TransDBHelper;
import com.smobs.models.TransReaderContract;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {

	private Button addTrans;
	private TransDBHelper transDBHelper;
	private SimpleCursorAdapter simpleCursorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		transDBHelper = new TransDBHelper(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addTrans = (Button) findViewById(R.id.button_add_trans);
		addTrans.setOnClickListener(this);

		SQLiteDatabase readableDatabase = transDBHelper.getReadableDatabase();
		Cursor query = readableDatabase.query(true,
				TransReaderContract.TransEntry.TABLE_NAME,
				TransReaderContract.allColumns(), null, null, null, null, null,
				null);
		if (query.getCount() > 0) {
			String[] columns = new String[] {
					TransReaderContract.TransEntry.PERSON_NAME,
					TransReaderContract.TransEntry.TRANS_AMOUNT,
					TransReaderContract.TransEntry.TRANS_TYPE };
			int[] to = new int[] { R.id.trans_list_name,
					R.id.trans_list_amount, R.id.trans_list_type };
			simpleCursorAdapter = new SimpleCursorAdapter(this,
					R.layout.trans_list_item, query, columns, to, 0);
			ListView trans_list_view = (ListView) findViewById(R.id.trans_list);
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
	public void onClick(View v) {
		Intent intent = new Intent(this, AddTransActivity.class);
		startActivity(intent);

	}

}

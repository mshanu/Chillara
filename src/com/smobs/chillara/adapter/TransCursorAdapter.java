package com.smobs.chillara.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.smobs.chillara.R;

public class TransCursorAdapter extends SimpleCursorAdapter {


    private Context context;

    public TransCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.context = context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView transTextView = (TextView) view.findViewById(R.id.trans_list_type);
        if (transTextView.getText().toString().equals("Credit")) {
            transTextView.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            transTextView.setTextColor(context.getResources().getColor(R.color.red));
        }

    }
}

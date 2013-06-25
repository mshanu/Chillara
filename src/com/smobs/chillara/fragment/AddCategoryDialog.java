package com.smobs.chillara.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.smobs.chillara.R;
import com.smobs.models.TransDBHelper;

public class AddCategoryDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater activity = getActivity().getLayoutInflater();
        builder.setView(activity.inflate(R.layout.add_new_category, null)).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView newCategoryText = (TextView) getDialog().findViewById(R.id.add_trans_add_category);
                        new TransDBHelper(getActivity()).addNewCategory(newCategoryText.getText().toString());

                    }
                }).
                setNegativeButton("Cancel", null).
                setTitle("New Category");


        return builder.create();
    }
}

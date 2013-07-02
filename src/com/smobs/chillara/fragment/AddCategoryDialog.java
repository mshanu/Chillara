package com.smobs.chillara.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.smobs.chillara.R;
import com.smobs.models.TransDBHelper;

public class AddCategoryDialog extends DialogFragment {

    private AddCategoryReactions categoryReactions;

    public interface AddCategoryReactions {
        public void onCategorySave();
        public void onCategoryCancel();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater activity = getActivity().getLayoutInflater();
        final View dialogView = activity.inflate(R.layout.add_new_category, null);
        builder.setView(dialogView).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView newCategoryText = (TextView) dialogView.findViewById(R.id.add_trans_new_category);
                        new TransDBHelper(getActivity()).addNewCategory(newCategoryText.getText().toString());
                        categoryReactions.onCategorySave();

                    }
                }).
                setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categoryReactions.onCategoryCancel();
                    }
                }).
                setTitle("New Category");


        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        categoryReactions = (AddCategoryReactions) activity;
    }
}

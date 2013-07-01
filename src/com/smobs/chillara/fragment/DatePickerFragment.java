package com.smobs.chillara.fragment;

import android.app.DatePickerDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import com.smobs.chillara.R;
import com.smobs.models.MyDateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.smobs.models.MyDateFormat.getMyDateFormat;
import static java.util.Calendar.*;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, calendar.get(YEAR), calendar.get(MONTH), calendar.get(DATE));
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
        Button transDate = (Button) getActivity().findViewById(R.id.add_trans_date);
        transDate.setText(getMyDateFormat().format(new Date(year, month, date)));
    }
}

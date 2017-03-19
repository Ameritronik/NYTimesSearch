package com.codepath.nytimessearch.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;

// Use this fragment to get date frompicker
public class SelectDateFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        int year;
        int month;
        int day;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month++; // increment by one to take care of mod count
            SearchSettingsFragment.DatefromPicker(year,month,day);
        }
}
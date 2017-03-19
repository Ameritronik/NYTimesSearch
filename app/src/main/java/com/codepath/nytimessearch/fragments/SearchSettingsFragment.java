package com.codepath.nytimessearch.fragments;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.nytimessearch.R;
import com.codepath.nytimessearch.activities.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchSettingsFragment extends DialogFragment {
    @Bind(R.id.spOrder)     Spinner spOrder;
    @Bind(R.id.btnOK)       Button okSave;
    @Bind(R.id.btnBack)     Button backNoSave;
    @Bind(R.id.etOnDate)    EditText etNewOnDate;
    @Bind(R.id.Arts)        CheckBox artsChecked;
    @Bind(R.id.style)       CheckBox styleChecked;
    @Bind(R.id.sports)      CheckBox sportsChecked;
    private static StringBuilder   mDateBuilder ;
    private static int year;
    private static int month;
    private static int day;
    Map<Long, SearchActivity.Order> sortLookUp = new HashMap<>();
    {
        sortLookUp.put(0L, SearchActivity.Order.RECENT);
        sortLookUp.put(1L, SearchActivity.Order.OLD);
    }

    private void ResetOrder() {
        SearchActivity.QueryDefaultValues.setOrder(
                sortLookUp.get(spOrder.getSelectedItemId()) );
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_settings, container, false);
        ButterKnife.bind(this, rootView);
        getDialog().setTitle("Search Settings");
        // ArrayAdapter con convert spinner to string
        ArrayAdapter<CharSequence> spinnerArrayAdapter = ArrayAdapter.createFromResource(
                getContext(),R.array.ordered_list,android.R.layout.simple_spinner_dropdown_item
        );
        // Specify the layout to use when the list of choices appears
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Connect spinner & adapter
        spOrder.setAdapter(spinnerArrayAdapter);
        spOrder.setSelection(0, true);
        // get new date builder
        mDateBuilder = new StringBuilder();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        etNewOnDate.setText(SearchActivity.QueryDefaultValues.getToShow());
        for (Map.Entry<Long, SearchActivity.Order> entry : sortLookUp.entrySet()) {
            if (SearchActivity.QueryDefaultValues.getOrder().equals(entry.getValue())) {
                spOrder.setSelection(entry.getKey().intValue());
            }
        }
        for (SearchActivity.SelectedNews sNews: SearchActivity.QueryDefaultValues.getNewSelected()) {
            if (SearchActivity.SelectedNews.ARTS.equals(sNews)) {
                artsChecked.setChecked(true);
            } else {
                artsChecked.setChecked(false);
            }
            if (SearchActivity.SelectedNews.SPORTS.equals(sNews)) {
                sportsChecked.setChecked(true);
            } else {
                sportsChecked.setChecked(false);
            }
            if (SearchActivity.SelectedNews.FASHION_AND_STYLE.equals(sNews)) {
                styleChecked.setChecked(true);
            } else {
                styleChecked.setChecked(false);
            }
        }

        okSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.QueryDefaultValues.setOnDate(mDateBuilder.toString());
                SearchActivity.QueryDefaultValues.setToShow(etNewOnDate.getText().toString());
                SelectedNewsUpdate();
                ResetOrder();
                ((SearchActivity)getActivity()).resetAdapter(SearchActivity.defaultQuery);
                dismiss();
            }
        });

        backNoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        etNewOnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                SelectDateFragment newSelDateFrgmt = new SelectDateFragment();
                newSelDateFrgmt.show(fm, "SelectDates");
                setDate();
            }
        });
    }
    public static void DatefromPicker(int y, int m, int d) {
        year = y;
        month=m;
        day=d;
    }
    //Format is 4 digits year, followed by 2 digits month and 2 for days
    public void setDate() {
        etNewOnDate.setText(year + "/" + month + "/" + day);
        mDateBuilder = new StringBuilder();
        mDateBuilder.append(year);
        // force two digits for month
        if (month > 9) { mDateBuilder.append(month); }
        else { mDateBuilder.append("0" + month); }
        // force two digits for day
        if( day > 9 ) { mDateBuilder.append(day); }
        else { mDateBuilder.append("0" + day);}
    }

    private void SelectedNewsUpdate() {
        List<SearchActivity.SelectedNews> newNews = new ArrayList<SearchActivity.SelectedNews>();

        if (artsChecked.isChecked()) {
            newNews.add(SearchActivity.SelectedNews.ARTS);
        }
        if (styleChecked.isChecked()) {
            newNews.add(SearchActivity.SelectedNews.FASHION_AND_STYLE);
        }
        if (sportsChecked.isChecked()) {
            newNews.add(SearchActivity.SelectedNews.SPORTS);
        }

        SearchActivity.QueryDefaultValues.setNewSelected(newNews);
    }
}

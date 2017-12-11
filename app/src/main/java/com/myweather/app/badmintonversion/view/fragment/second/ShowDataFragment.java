package com.myweather.app.badmintonversion.view.fragment.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myweather.app.badmintonversion.R;


/**
 * Created by zyt on 2017/8/15.
 */

public class ShowDataFragment extends android.support.v4.app.Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";
    public ShowDataFragment() {
    }


    String [][] data = {{"1","2","3"},{"5","6","7"},{"8","9","10"}};
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ShowDataFragment newInstance(int sectionNumber) {
        ShowDataFragment fragment = new ShowDataFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment__show_data, container, false);
        TextView timeTextView = (TextView) rootView.findViewById(R.id.time_text_view);
        TextView carielloTextView = (TextView) rootView.findViewById(R.id.cariello_text_view);
        TextView countTextView = (TextView) rootView.findViewById(R.id.count_text_view);


        timeTextView.setText(data[getArguments().getInt(ARG_SECTION_NUMBER)-1][0]);
        carielloTextView.setText(data[getArguments().getInt(ARG_SECTION_NUMBER)-1][1]);
        countTextView.setText(data[getArguments().getInt(ARG_SECTION_NUMBER)-1][2]);


        return rootView;
    }
}

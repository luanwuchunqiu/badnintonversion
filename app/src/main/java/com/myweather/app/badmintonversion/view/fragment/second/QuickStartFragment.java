package com.myweather.app.badmintonversion.view.fragment.second;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.myweather.app.badmintonversion.R;


/**
 * Created by zyt on 2017/8/17.
 */

public class QuickStartFragment extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    public QuickStartFragment() {
    }


    String [][] data = {{"1","2"},{"5","6"},{"8","9"},{"10","11"},{"11","12"}};
    int[] bgs = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp,
            R.drawable.ic_explore_24dp,R.drawable.ic_call_24dp,
    R.drawable.ic_query_builder_24dp};
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static QuickStartFragment newInstance(int sectionNumber) {
        QuickStartFragment fragment = new QuickStartFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quick_start, container, false);
        TextView quickStartTextViewBig = (TextView) rootView.findViewById(R.id.quick_start_textView_big);
        TextView quickStartTextViewSmall = (TextView) rootView.findViewById(R.id.quick_start_textView_small);
        ImageView quickStartImageView = (ImageView) rootView.findViewById(R.id.quick_start_imageView) ;

        quickStartTextViewBig.setText(data[getArguments().getInt(ARG_SECTION_NUMBER)-1][0]);

        quickStartTextViewSmall.setText(data[getArguments().getInt(ARG_SECTION_NUMBER)-1][1]);

        quickStartImageView.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);


        return rootView;
    }
}

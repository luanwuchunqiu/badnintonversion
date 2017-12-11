package com.myweather.app.badmintonversion.view.fragment.second;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.myweather.app.badmintonversion.R;

/**
 * Created by zyt on 2017/9/8.
 */

public class InstallFragment extends Fragment{

    private static final String SECTIONNUM = "section";
    private TextView installTextView ;
    private ImageView installImageView;
    public InstallFragment(){};
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    private int[] imageIds = new int[]{R.drawable.ic_flight_24dp, R.drawable.ic_mail_24dp,
            R.drawable.ic_explore_24dp};
    private String[] textViews;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static InstallFragment newInstance(int num){
        InstallFragment fragment = new InstallFragment();
        Bundle args = new Bundle();
        args.putInt(SECTIONNUM,num);
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        textViews = new String[]{
               getActivity().getResources().getString(R.string.install_step1),
                getActivity().getResources().getString(R.string.install_step2),
                getActivity().getResources().getString(R.string.install_step3)};
        View view  = inflater.inflate(R.layout.fragment_install,container,false);
        installTextView = (TextView) view.findViewById(R.id.install_text_view);
        installImageView =(ImageView) view.findViewById(R.id.install_image_view);
        installTextView.setText(textViews[getArguments().getInt(SECTIONNUM)]);
        installImageView.setBackgroundResource(imageIds[getArguments().getInt(SECTIONNUM)]);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

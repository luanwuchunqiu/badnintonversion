package com.myweather.app.badmintonversion.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.view.adapter.InstallViewPaperAdapter;

/**
 * Created by zyt on 2017/9/8.
 */

public class InstallActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager installViewPaper;
    private TextView[] stepTextViews;
    private TextView step1TextView;
    private TextView step2TextView;
    private TextView step3TextView;
    private ImageView[] indicators;
    private ImageView indicator1;
    private ImageView indicator2;
    private  ImageView indacator3;
    private ViewPager viewPager;
    private InstallViewPaperAdapter adapter;
    private ImageView backImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
        backImageView = (ImageView) findViewById(R.id.install_start_back);
        step1TextView = (TextView) findViewById(R.id.install_step1);
        step2TextView = (TextView) findViewById(R.id.install_step2);
        step3TextView = (TextView) findViewById(R.id.install_step3);
        indicator1 = (ImageView) findViewById(R.id.quick_indicator_0);
        indicator2 = (ImageView) findViewById(R.id.quick_indicator_1);
        indacator3 = (ImageView) findViewById(R.id.quick_indicator_2);
        viewPager =   (ViewPager) findViewById(R.id.install_view_paper);
        stepTextViews = new TextView[]{step1TextView,step2TextView,step3TextView};
        indicators = new ImageView[]{indicator1,indicator2,indacator3};
        adapter = new InstallViewPaperAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        backImageView.setOnClickListener(this);

    }



    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected

            );
            stepTextViews[i].setTextColor(i == position ? getResources().getColor(R.color.green) :getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateIndicators(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.install_start_back:{
                onBackPressed();
            }break;
        }
    }
}

package com.myweather.app.badmintonversion.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.view.adapter.QuickStartViewPaperAdapter;


/**
 * Created by zyt on 2017/8/17.
 */

public class QiuckStartActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backImageView;
    private ViewPager quickStartViewPaper;
    private QuickStartViewPaperAdapter quickStartViewPaperAdapter;
    private ImageView zero,one,two,three,four;
    private TextView zeroText,oneText,twoText,threeText,fourText;
    private ImageView[] indicators;
    private TextView[] textViews;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_start);
        quickStartViewPaper = (ViewPager) findViewById(R.id.quick_start_viewPaper);
        quickStartViewPaperAdapter = new QuickStartViewPaperAdapter(getSupportFragmentManager());
        quickStartViewPaper.setAdapter(quickStartViewPaperAdapter);
        backImageView = (ImageView) findViewById(R.id.qquick_start_back);
        backImageView.setOnClickListener(this);

        zero = (ImageView) findViewById(R.id.quick_indicator_0);
        one = (ImageView) findViewById(R.id.quick_indicator_1);
        two = (ImageView) findViewById(R.id.quick_indicator_2);
        three = (ImageView) findViewById(R.id.quick_indicator_3);
        four = (ImageView) findViewById(R.id.quick_indicator_4);
        indicators = new ImageView[]{zero,one,two,three,four};



        zeroText = (TextView) findViewById(R.id.quick_turn_on_textView);
        oneText = (TextView) findViewById(R.id.quick_connect_textView);
        twoText = (TextView) findViewById(R.id.quick_status_textView);
        threeText = (TextView) findViewById(R.id.quick_synchronize_textView);
        fourText = (TextView) findViewById(R.id.quick_turnoff_textView);
        textViews = new TextView[]{zeroText,oneText,twoText,threeText,fourText};



        quickStartViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
        });
    }


    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected

            );
            textViews[i].setTextColor(i == position ? getResources().getColor(R.color.green) :getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.qquick_start_back):{
                    onBackPressed();
            }break;
        }
    }
}

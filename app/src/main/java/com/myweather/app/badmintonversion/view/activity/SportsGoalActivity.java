package com.myweather.app.badmintonversion.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.myweather.app.badmintonversion.R;
import com.xw.repo.BubbleSeekBar;

public class SportsGoalActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView goalBackImageView;
   private BubbleSeekBar sportsTimeSeekbar;
    private BubbleSeekBar sportsCountSeekbar;
    private BubbleSeekBar sportsCalSeekbar;
    private BubbleSeekBar sportsFrequenceSeekbar;
    private SharedPreferences sharedPreferences;
    private TextView  gaolSaveTextView;

   private TextView sportsTimeTextView;
    private TextView sportsCountTextView;
    private TextView sportsCalTextView;
    private TextView sportsFrequenceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_goal);
        goalBackImageView = (ImageView) findViewById(R.id.goal_back);
        gaolSaveTextView = (TextView) findViewById(R.id.goal_save);
        goalBackImageView.setOnClickListener(this);
        gaolSaveTextView.setOnClickListener(this);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sportsTimeSeekbar = (BubbleSeekBar) findViewById(R.id.sports_time_seekBar);
        sportsCountSeekbar = (BubbleSeekBar) findViewById(R.id.sports_count_seekBar);
        sportsCalSeekbar = (BubbleSeekBar) findViewById(R.id.sports_cal_seekBar);
        sportsFrequenceSeekbar = (BubbleSeekBar) findViewById(R.id.sports_frequence_seekBar);


        sportsTimeTextView = (TextView) findViewById(R.id.sports_time_textView);
        sportsCountTextView = (TextView) findViewById(R.id.sports_count_textView);
        sportsCalTextView = (TextView) findViewById(R.id.sports_cal_textView);
        sportsFrequenceTextView = (TextView) findViewById(R.id.sports_frequence_textView);


        float goalTime = sharedPreferences.getFloat("goal_time",0.0F);
        sportsTimeTextView.setText(goalTime+"");
        sportsTimeSeekbar.setProgress(goalTime);

        int goalCount = sharedPreferences.getInt("goal_count",0);
        sportsCountTextView.setText(goalCount+"");
        sportsCountSeekbar.setProgress(goalCount);

        int goalCal = sharedPreferences.getInt("goal_cal",0);
        sportsCalTextView.setText(goalCal+"");
        sportsCalSeekbar.setProgress(goalCal);

        int goalFre = sharedPreferences.getInt("goal_fre",0);
        sportsFrequenceTextView.setText(goalFre+"");
        sportsFrequenceSeekbar.setProgress(goalFre);




        sportsTimeSeekbar.setOnProgressChangedListener(new MyOnProgressChangedListener(sportsTimeTextView,true,"goal_time"));
        sportsCountSeekbar.setOnProgressChangedListener(new MyOnProgressChangedListener(sportsCountTextView,false,"goal_count"));
        sportsCalSeekbar.setOnProgressChangedListener(new MyOnProgressChangedListener(sportsCalTextView,false,"goal_cal"));
        sportsFrequenceSeekbar.setOnProgressChangedListener(new MyOnProgressChangedListener(sportsFrequenceTextView,false,"goal_fre"));





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goal_back:{onBackPressed();}break;
            case R.id.goal_save:{
                Toast.makeText(this,"保存数据成功",Toast.LENGTH_SHORT).show();}break;
        }

    }


    class MyOnProgressChangedListener implements BubbleSeekBar.OnProgressChangedListener{
        SharedPreferences.Editor editor =sharedPreferences.edit();


        private TextView textView;
        private boolean type;
        private String name;
        MyOnProgressChangedListener(TextView textView,boolean type,String name){
            this.textView = textView;
            this.type = type;
            this.name = name;
        }
        @Override
        public void onProgressChanged(int progress, float progressFloat) {
            if(type==true)
            {textView.setText(progressFloat+"");
                editor.putFloat(name,progressFloat);
            editor.apply();}
            else
            { textView.setText(progress+"");
                editor.putInt(name,progress);
                editor.apply();
            }
        }

        @Override
        public void getProgressOnActionUp(int progress, float progressFloat) {

        }

        @Override
        public void getProgressOnFinally(int progress, float progressFloat) {

        }
    }

}

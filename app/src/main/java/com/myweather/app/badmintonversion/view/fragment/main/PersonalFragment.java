package com.myweather.app.badmintonversion.view.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.UserModel;
import com.myweather.app.badmintonversion.view.activity.InstallActivity;
import com.myweather.app.badmintonversion.view.activity.LoginActivity;
import com.myweather.app.badmintonversion.view.activity.QiuckStartActivity;
import com.myweather.app.badmintonversion.view.activity.SettingActivity;
import com.myweather.app.badmintonversion.view.activity.SportsGoalActivity;
import com.myweather.app.badmintonversion.view.activity.UserDataActivity;
import com.myweather.app.badmintonversion.view.adapter.DataViewPaperAdapter;

import org.litepal.crud.DataSupport;

/**
 * Created by zyt on 2017/9/7.
 */

public class PersonalFragment extends Fragment implements View.OnClickListener {
    private LinearLayout sportsGoalLayout;
    private LinearLayout quickStartLayout;
    private LinearLayout userHeadLayout;
    private LinearLayout installLayout;
    private LinearLayout settingLayout;
    private DataViewPaperAdapter dataViewPaperAdapter;
    private ImageView userPhoto;
    private TextView userNameTextView;
    private int page;
    private View view;
    private ViewPager mViewPager;
    ImageView zero, one, two;
    ImageView[] indicators;
    private TextView dataCountTimeTextView;
    private UserModel userModel;
    private UserLocal userLocal;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {


            if(view==null) {
                view = inflater.inflate(R.layout.fragment_personal,container,false);




            }
                userModel = new UserModel();
                userPhoto = (ImageView) view.findViewById(R.id.profile_image);
                userNameTextView = (TextView) view.findViewById(R.id.userName);
               sportsGoalLayout = (LinearLayout) view.findViewById(R.id.layout_sports_goal);
                quickStartLayout = (LinearLayout) view.findViewById(R.id.layout_quick_start);
                userHeadLayout = (LinearLayout) view.findViewById(R.id.user_head_layout);
                installLayout = (LinearLayout) view.findViewById(R.id.install_layout);
                settingLayout = (LinearLayout) view.findViewById(R.id.layout_setting) ;


                sportsGoalLayout.setOnClickListener(this);
                quickStartLayout.setOnClickListener(this);
                userHeadLayout.setOnClickListener(this);
                installLayout.setOnClickListener(this);
                settingLayout.setOnClickListener(this);




                if(!userModel.isLogin()){
                    userNameTextView.setText("未登录");

                }
                // fragment中嵌套viewpager，vierpager中有多个fragment，不显示 ...
                //用Fragment中的getChildFragmentManager代替Activity中的getSupportFragmentManager()解决子Fragment不显示的问题
                dataViewPaperAdapter = new DataViewPaperAdapter(getChildFragmentManager());
                mViewPager = (ViewPager) view.findViewById(R.id.data_show_viewPaper);
                mViewPager.setAdapter(dataViewPaperAdapter);
                zero = (ImageView) view.findViewById(R.id.intro_indicator_0);
                one = (ImageView) view.findViewById(R.id.intro_indicator_1);
                two = (ImageView) view.findViewById(R.id.intro_indicator_2);
                indicators = new ImageView[]{zero, one, two};
                dataCountTimeTextView = (TextView) view.findViewById(R.id.data_count_time);
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        page = position;
                        updateIndicators(page);

                        switch (position) {
                            case 0:
                                dataCountTimeTextView.setText("近7天");
                                break;
                            case 1:
                                dataCountTimeTextView.setText("近30天");
                                break;
                            case 2:
                                dataCountTimeTextView.setText("累计");
                                break;
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });



        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }

    public void fresh(String string){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.layout_sports_goal): {


                Intent intent = new Intent(getActivity(),SportsGoalActivity.class);
                startActivity(intent);

            };break;
            case (R.id.layout_quick_start):{
                Intent intent = new Intent(getActivity(),QiuckStartActivity.class);
                startActivity(intent);
            }break;
            case(R.id.user_head_layout):{
                if(new UserModel().isLogin())
                {Intent intent = new Intent(getActivity(),UserDataActivity.class);
                startActivity(intent);}
                else {
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            }break;
            case(R.id.install_layout):{
                Intent intent = new Intent(getActivity(),InstallActivity.class);
                startActivity(intent);
            }break;
            case (R.id.layout_setting):{
                Intent intent = new Intent(getActivity(),SettingActivity.class);
                startActivity(intent);
            }break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        //在onResume()中进行组件的更新，能够保证及时更新
        //看是否有用户登录，如果有就进行初始化
        if(userModel.isLogin()){
            userLocal = DataSupport.findFirst(UserLocal.class);
            userNameTextView.setText(userLocal.getUsername());
            if(userLocal.getUserPhoto()!=null){
                Glide.with(this).load(userLocal.getUserPhoto()).into(userPhoto);
            }
        }
    }
}

package com.myweather.app.badmintonversion.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.User;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserModel;
import com.myweather.app.badmintonversion.view.picker.common.util.ConvertUtils;
import com.myweather.app.badmintonversion.view.picker.pickertask.AddressPickTask;
import com.myweather.app.badmintonversion.view.picker.wheelpicker.entity.City;
import com.myweather.app.badmintonversion.view.picker.wheelpicker.entity.County;
import com.myweather.app.badmintonversion.view.picker.wheelpicker.entity.Province;
import com.myweather.app.badmintonversion.view.picker.wheelpicker.picker.DatePicker;
import com.myweather.app.badmintonversion.view.picker.wheelpicker.picker.NumberPicker;
import com.myweather.app.badmintonversion.view.picker.wheelpicker.picker.OptionPicker;
import com.myweather.app.badmintonversion.view.picker.wheelpicker.widget.WheelView;


import org.litepal.crud.DataSupport;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;


/**
 * Created by zyt on 2017/8/21.
 */

public class UserDataActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout genderLayout;
    private LinearLayout userHeightLayout;
    private LinearLayout userWeightLayout;
    private LinearLayout userAddressLayout;
    private LinearLayout userBirthdayLayout;
    private LinearLayout userSportsYearLayout;
    private UserModel userModel;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private  TextView    genderTextView;
    private  TextView userHeightTextView;
    private  TextView userWeightTextView;
    private  TextView userAddressTextView;
    private ImageView userBack;
    private TextView saveTextView;
    private  TextView userBirthdayTextView;
    private  TextView userSportsYearTextView;
    private TextView userBmiNum;
    private  TextView userBmiText;
    private TextView usernameTextView;
    private TextView statusTextView;
    private ImageView userPhoto;
    private ImageView editUserName;
    private ImageView editUserstatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        userModel = new UserModel();
        usernameTextView = (TextView) findViewById(R.id.userName);
        statusTextView = (TextView) findViewById(R.id.status);
        userPhoto = (ImageView) findViewById(R.id.profile_image);
        userBack = (ImageView)findViewById(R.id.user_data_back);
        saveTextView = (TextView) findViewById(R.id.user_data_save);
        genderLayout = (LinearLayout) findViewById(R.id.gender);
        userHeightLayout = (LinearLayout) findViewById(R.id.layout_user_height);
        userWeightLayout = (LinearLayout) findViewById(R.id.layout_user_weight);
        userAddressLayout = (LinearLayout) findViewById(R.id.layout_user_address);
        userBirthdayLayout = (LinearLayout) findViewById(R.id.layout_user_birthday);
        userSportsYearLayout = (LinearLayout) findViewById(R.id.layout_user_sports_year);
        editUserName = (ImageView) findViewById(R.id.image_edit_username);
        editUserName.setOnClickListener(this);
        editUserstatus = (ImageView) findViewById(R.id.image_edit_status);
        editUserstatus.setOnClickListener(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        genderTextView = (TextView) findViewById(R.id.genderTextView) ;
        userHeightTextView = (TextView) findViewById(R.id.text_view_user_height);
        userWeightTextView = (TextView) findViewById(R.id.text_view_user_weight);
        userAddressTextView= (TextView) findViewById(R.id.text_view_user_address);
        userBirthdayTextView = (TextView) findViewById(R.id.text_view_user_birthday);
        userSportsYearTextView = (TextView) findViewById(R.id.text_view_user_sports_year);
        userBmiNum = (TextView) findViewById(R.id.text_view_user_bmi) ;
        userBmiText = (TextView) findViewById(R.id.text_view_user_bmi_text);

        userPhoto.setOnClickListener(this);
        userBack.setOnClickListener(this);
        saveTextView.setOnClickListener(this);
        genderLayout.setOnClickListener(this);
        userHeightLayout.setOnClickListener(this);
        userWeightLayout.setOnClickListener(this);
        userAddressLayout.setOnClickListener(this);
        userBirthdayLayout.setOnClickListener(this);
        userSportsYearLayout.setOnClickListener(this);



        UserLocal userLocal = DataSupport.findFirst(UserLocal.class);



        if(userLocal!=null){
            usernameTextView.setText(userLocal.getUsername());
            if(userLocal.getUserPhoto()!=null){

            Glide.with(this).load(userLocal.getUserPhoto()).into(userPhoto);}


        }

        if(sharedPreferences.getString("user_gender","")!=null){
            genderTextView.setText(sharedPreferences.getString("user_gender",""));
        };
        if(sharedPreferences.getInt("user_height",0)!=0){
            userHeightTextView.setText(sharedPreferences.getInt("user_height",0)+"");
        };
        if(sharedPreferences.getInt("user_weight",0)!=0){
            userWeightTextView.setText(sharedPreferences.getInt("user_weight",0)+"");
        };
        if(sharedPreferences.getString("user_birthday","")!=null){
            userBirthdayTextView.setText(sharedPreferences.getString("user_birthday",""));
        };
        if(sharedPreferences.getString("user_address","")!=null){
            userAddressTextView.setText(sharedPreferences.getString("user_address",""));
        };
        if(sharedPreferences.getString("user_year","")!=null){
            userSportsYearTextView.setText(sharedPreferences.getString("user_year",""));
        };
        setBmi();
    }

    @Override
    public void onClick(View v) {



        switch (v.getId()){
            case R.id.image_edit_username:{
                startActivity(new Intent(this,EditUserNameActivity.class));
            }break;
            case R.id.image_edit_status:{

            }break;
            case R.id.profile_image:{
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
            }break;
            case R.id.user_data_back:{
                onBackPressed();
            }break;
            case R.id.user_data_save:{
                Toast.makeText(this, "保存数据成功", Toast.LENGTH_SHORT).show();
            }break;
            case R.id.gender:{
                OptionPicker picker = new OptionPicker(this, new String[]{
                        "男", "女"
                });

                picker.setCanceledOnTouchOutside(true);
                picker.setDividerRatio(WheelView.DividerConfig.FILL);
                picker.setShadowColor(Color.parseColor("#ffffff"));
                picker.setSelectedIndex(1);
                picker.setCycleDisable(true);
                picker.setBackgroundColor(Color.WHITE);
                picker.setTitleTextColor(Color.parseColor("#f23030"));

                //设置分割线的颜色
                picker.setDividerColor(Color.parseColor("#e3e5e9"));
                picker.setDividerVisible(false);
                //提交文字颜色
                picker.setSubmitTextColor(Color.parseColor("#232326"));
                picker.setTextColor(Color.parseColor("#f23030"),Color.GRAY);


                //按下后文字的颜色
                picker.setPressedTextColor(Color.parseColor("#f23030"));
                picker.setCancelTextColor(Color.parseColor("#232326"));
                picker.setTopLineColor(Color.parseColor("#e3e5e9"));
                picker.setTextSize(15);
                picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        genderTextView.setText(item);
                        editor.putString("user_gender",item);
                        editor.apply();

                    }
                });
                picker.show();
            }break;

            case R.id.layout_user_height:{
                NumberPicker picker = new NumberPicker(this);
                picker.setWidth(picker.getScreenWidthPixels() );//选择器的宽度
                picker.setSubmitTextColor(Color.parseColor("#232326"));
                picker.setTextColor(Color.parseColor("#f23030"),Color.GRAY);


                //按下后文字的颜色
                picker.setPressedTextColor(Color.parseColor("#f23030"));
                picker.setCancelTextColor(Color.parseColor("#232326"));
                picker.setTopLineColor(Color.parseColor("#e3e5e9"));
                picker.setCycleDisable(false);
                picker.setDividerVisible(false);
                picker.setOffset(2);//偏移量
                picker.setRange(145, 200, 1);//数字范围
                picker.setSelectedItem(172);
                picker.setLabel("厘米");
                picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
                    @Override
                    public void onNumberPicked(int index, Number item) {
                        userHeightTextView.setText(item.intValue()+"");
                        setBmi();
                        editor.putInt("user_height",item.intValue());
                        editor.apply();
                    }
                });
                picker.show();
            }break;
            case R.id.layout_user_weight:{
                NumberPicker picker = new NumberPicker(this);
                picker.setWidth(picker.getScreenWidthPixels() );//选择器的宽度
                picker.setSubmitTextColor(Color.parseColor("#232326"));
                picker.setTextColor(Color.parseColor("#f23030"),Color.GRAY);


                //按下后文字的颜色
                picker.setPressedTextColor(Color.parseColor("#f23030"));
                picker.setCancelTextColor(Color.parseColor("#232326"));
                picker.setTopLineColor(Color.parseColor("#e3e5e9"));
                picker.setCycleDisable(false);
                picker.setDividerVisible(false);
                picker.setOffset(2);//偏移量
                picker.setRange(30, 200, 1);//数字范围
                picker.setSelectedItem(50);
                picker.setLabel("kg");
                picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
                    @Override
                    public void onNumberPicked(int index, Number item) {
                        userWeightTextView.setText(item.intValue()+"");
                        setBmi();
                        editor.putInt("user_weight",item.intValue());

                        editor.apply();
                    }
                });
                picker.show();
            }break;
            case R.id.layout_user_address:{

                AddressPickTask task = new AddressPickTask(this);
                task.setHideProvince(false);
                task.setHideCounty(false);
                task.setCallback(new AddressPickTask.Callback() {
                    @Override
                    public void onAddressInitFailed() {
                        showToast("数据初始化失败");
                    }

                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        if (county == null) {
                            userAddressTextView.setText(province.getName()+" "+city.getName());
                            editor.putString("user_address",province.getName()+" "+city.getName());
                        } else {
                            userAddressTextView.setText(province.getName()+" "+city.getName()+" "+county.getName());
                            editor.putString("user_address",province.getName()+" "+city.getName()+" "+county.getName());
                            editor.apply();
                        }
                    }
                });
                task.execute("北京", "北京", "东城");

            }break;
            case R.id.layout_user_birthday:{
                final DatePicker picker = new DatePicker(this);

                picker.setCanceledOnTouchOutside(true);
                picker.setUseWeight(true);
                picker.setSubmitTextColor(Color.parseColor("#232326"));
                picker.setTextColor(Color.parseColor("#f23030"),Color.GRAY);


                //按下后文字的颜色
                picker.setPressedTextColor(Color.parseColor("#f23030"));
                picker.setCancelTextColor(Color.parseColor("#232326"));
                picker.setTopLineColor(Color.parseColor("#e3e5e9"));
                picker.setTopPadding(ConvertUtils.toPx(this, 10));
                picker.setRangeEnd(2017, 8, 29);
                picker.setRangeStart(1901, 8, 29);
                picker.setDividerVisible(false);
                picker.setSelectedItem(1992, 10, 14);
                picker.setResetWhileWheel(false);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        userBirthdayTextView.setText(year+"-"+month+"-"+day);
                        editor.putString("user_birthday",year+"-"+month+"-"+day);
                        editor.apply();
                    }
                });
                picker.setOnWheelListener(new DatePicker.OnWheelListener() {
                    @Override
                    public void onYearWheeled(int index, String year) {
                        picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onMonthWheeled(int index, String month) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
                    }

                    @Override
                    public void onDayWheeled(int index, String day) {
                        picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
                    }
                });
                picker.show();
            }break;
            case R.id.layout_user_sports_year:{

                NumberPicker picker = new NumberPicker(this);
                picker.setWidth(picker.getScreenWidthPixels() );//选择器的宽度
                picker.setCycleDisable(false);
                picker.setDividerVisible(false);
                picker.setSubmitTextColor(Color.parseColor("#232326"));
                picker.setTextColor(Color.parseColor("#f23030"),Color.GRAY);


                //按下后文字的颜色
                picker.setPressedTextColor(Color.parseColor("#f23030"));
                picker.setCancelTextColor(Color.parseColor("#232326"));
                picker.setTopLineColor(Color.parseColor("#e3e5e9"));
                picker.setOffset(2);//偏移量
                picker.setRange(1995, 2017, 1);//数字范围
                picker.setSelectedItem(2017);
                picker.setLabel("年");
                picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
                    @Override
                    public void onNumberPicked(int index, Number item) {
                        userSportsYearTextView.setText(2017-item.intValue()+"");
                        editor.putString("user_year",2017-item.intValue()+"");
                        editor.apply();
                    }
                });
                picker.show();
            }break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserLocal userLocal = DataSupport.findFirst(UserLocal.class);
        usernameTextView.setText(userLocal.getUsername());
    }

    public void setBmi(){
    int height = Integer.parseInt(userHeightTextView.getText().toString());
    int weight = Integer.parseInt(userWeightTextView.getText().toString());
    DecimalFormat df = new DecimalFormat("0.0");
    double bmi = (weight)/((height/100.0)*(height/100.0));
            userBmiNum.setText(df.format(bmi)+"");
            if(bmi<18.5){userBmiText.setText("偏瘦型");}
            else if(bmi>24.9){userBmiText.setText("偏胖型");}
            else{userBmiText.setText("正常型");}
}
private void showToast(String tag) {
    Toast.makeText(this,tag,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                final ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                final UserLocal userLocal = DataSupport.findAll(UserLocal.class).get(0);
                Toast.makeText(this, photos.get(0), Toast.LENGTH_SHORT).show();
                userModel.updateUserPhoto(photos.get(0), userLocal.getObjectId(), new BaseListener() {
                    @Override
                    public void getSuccess(Object o) {
                        Toast.makeText(UserDataActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        User user = (User) o;
                        Glide.with(UserDataActivity.this).load(user.getUserPhoto().getUrl()).into(userPhoto);
                        userLocal.setUserPhoto(photos.get(0));
                        userLocal.save();

                    }

                    @Override
                    public void getFailure(Exception e) {

                    }
                });



            }
        }
    }
}

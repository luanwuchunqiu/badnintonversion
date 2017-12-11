package com.myweather.app.badmintonversion.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.UserDataLocal;
import com.myweather.app.badmintonversion.entity.UserLocal;

import org.litepal.crud.DataSupport;

/**
 * Created by zyt on 2017/9/11.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backImageView;
    private LinearLayout userDataLayout;
    private Button unregisterButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        unregisterButton = (Button) findViewById(R.id.button_unregister);
        unregisterButton.setOnClickListener(this);
        backImageView = (ImageView) findViewById(R.id.setting_back);
        backImageView.setOnClickListener(this);
        userDataLayout = (LinearLayout) findViewById(R.id.layout_user_data);
        userDataLayout.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //保存一些数据
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back:{onBackPressed();}break;
            case R.id.layout_user_data:{startActivity(new Intent(SettingActivity.this,LoginActivity.class));}break;
            case R.id.button_unregister:{
                DataSupport.deleteAll(UserLocal.class);
                DataSupport.deleteAll(UserDataLocal.class);
                Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
            }break;
        }
    }
}

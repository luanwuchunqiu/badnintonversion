package com.myweather.app.badmintonversion.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserModel;

/**
 * Created by zyt on 2017/10/16.
 */


public class EditUserNameActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editUsername;
    private ImageView backImage;
    private TextView saveImage;
    private UserModel userModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_name);
        editUsername = (EditText) findViewById(R.id.edit_text_edit_username);
        backImage = (ImageView) findViewById(R.id.image_back);
        saveImage =(TextView) findViewById(R.id.text_save);
        userModel = new UserModel();
        editUsername.setOnClickListener(this);
        backImage.setOnClickListener(this);
        saveImage.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_text_edit_username:{


            }break;
            case R.id.image_back:{
                onBackPressed();

            }break;
            case R.id.text_save:{
                final String username = editUsername.getText().toString();
                if(!TextUtils.isEmpty(username)){
                    final UserLocal userLocal = userModel.getUserLocal();
                   userModel.updateUsername(username, userLocal.getObjectId(), new BaseListener() {
                       @Override
                       public void getSuccess(Object o) {
                           Toast.makeText(EditUserNameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                           userLocal.setUsername(username);
                           userLocal.save();
                           finish();
                       }

                       @Override
                       public void getFailure(Exception e) {

                       }
                   });
                }

            }break;
        }
    }
}

package com.myweather.app.badmintonversion.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.User;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserModel;

/**
 * Created by zyt on 2017/10/15.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText accountEditText;
    private EditText passwordEditText;
    private EditText usernameEditText;
    private Button registerButton;
    private UserModel userModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userModel = new UserModel();
        accountEditText = (EditText) findViewById(R.id.edit_text_account);
        passwordEditText = (EditText) findViewById(R.id.edit_text_password);
        registerButton = (Button) findViewById(R.id.button_register);
        usernameEditText = (EditText) findViewById(R.id.edit_text_username);

        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.button_register):{
                final User user = new User();
                user.setUserAccount(accountEditText.getText().toString());
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                //判断用户是否被注册 如果没有被注册 那么注册
                userModel.isAccountResgister(accountEditText.getText().toString(), new BaseListener() {
                    @Override
                    public void getSuccess(Object o) {
                        Toast.makeText(RegisterActivity.this, "账号已经被注册", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void getFailure(Exception e) {
                        userModel.onRegister(user, new BaseListener() {
                            @Override
                            public void getSuccess(Object o) {
                                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            }

                            @Override
                            public void getFailure(Exception e) {
                                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
               /* */


            }break;

        }
    }
}

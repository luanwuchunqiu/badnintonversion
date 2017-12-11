package com.myweather.app.badmintonversion.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

;
import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.User;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText accountEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userModel = new UserModel();
        accountEditText = (EditText) findViewById(R.id.edit_text_account);
        passwordEditText = (EditText) findViewById(R.id.edit_text_password);
        loginButton = (Button) findViewById(R.id.button_login);
        registerTextView = (TextView) findViewById(R.id.text_view_register);
        loginButton.setOnClickListener(this);
        registerTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.button_login):{

                if(!TextUtils.isEmpty(accountEditText.getText().toString())&&!TextUtils.isEmpty(passwordEditText.getText().toString())){
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("");
                    progressDialog.setMessage("登录中...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    userModel.getUser(accountEditText.getText().toString(), passwordEditText.getText().toString(), new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            User user = (User) o;
                            UserLocal userLocal = new UserLocal();
                            userLocal.setObjectId(user.getObjectId());
                            userLocal.setUserAccount(user.getUserAccount());
                            userLocal.setPassword(user.getPassword());
                            userLocal.setUsername(user.getUsername());
                            if(user.getUserPhoto()!=null){
                                userLocal.setUserPhoto(user.getUserPhoto().getUrl());
                            }
                            userModel.putUserLocal(userLocal);
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this,UserDataActivity.class));

                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                        @Override
                        public void getFailure(Exception e) {

                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }break;
            case(R.id.text_view_register):{
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }break;

        }
    }
}

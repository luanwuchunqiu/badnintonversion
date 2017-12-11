package com.myweather.app.badmintonversion;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.myweather.app.badmintonversion.dao.UserDataDaoImpl;
import com.myweather.app.badmintonversion.entity.UserDataLocal;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserDataService;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;

public class PopupActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private Button button;
    private Button findButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        Bmob.initialize(this, "b8d500b1a2f90a1df5a27a0c357a0f8a");
        textView = (TextView) findViewById(R.id.text);
        button = (Button)findViewById(R.id.save_data);
        findButton = (Button)findViewById(R.id.findButton) ;
        findButton.setOnClickListener(this);
       button.setOnClickListener(this);
        textView.setOnClickListener(this);
        SQLiteDatabase db = LitePal.getDatabase();


    }

    private void showPopupMenu(View view) {
        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(PopupActivity.this, view);
        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.device, popupMenu.getMenu());

        popupMenu.show();
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.text:{
                showPopupMenu(v);
                UserDataLocal userDataLocal = new UserDataLocal();
                userDataLocal.setUserId("oooo");
                userDataLocal.save();

            }break;
            case R.id.save_data:{
                Date date = new Date();
                //String week = new SimpleDateFormat("EEEE").format(date);
                String localDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                ArrayList<String> array2 = new ArrayList<String>();
                array2.add(localDate);array2.add("3");array2.add("4");
                array2.add("5");array2.add("6");array2.add("7");
                array2.add("8");array2.add("9");array2.add("10");



                new UserDataService().saveOrUpdate("53b928c390", array2, new BaseListener() {
                    @Override
                    public void getSuccess(Object o) {
                        Toast.makeText(PopupActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void getFailure(Exception e) {
                        Toast.makeText(PopupActivity.this, "保存失败", Toast.LENGTH_SHORT).show();

                    }
                });


            }break;
            case R.id.findButton:{
                new UserDataDaoImpl().findByUserId("c4a730543b", new BaseListener() {
                    @Override
                    public void getSuccess(Object o) {
                        Toast.makeText(PopupActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void getFailure(Exception e) {
                        Toast.makeText(PopupActivity.this, "查询失败", Toast.LENGTH_SHORT).show();

                    }
                });
            }break;
        }
    }
}

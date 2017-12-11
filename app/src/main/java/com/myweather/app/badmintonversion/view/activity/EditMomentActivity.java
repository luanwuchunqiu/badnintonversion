package com.myweather.app.badmintonversion.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.Moment;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.MomentModel;
import com.myweather.app.badmintonversion.view.adapter.MomentPictureAdapter;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by zyt on 2017/10/17.
 */

public class EditMomentActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView pictureRecyler;
    private MomentPictureAdapter momentPictureAdapter;
    private ArrayList<String> urls;
    private static final String ADD_IMAGE = "file:///android_asset/zx.png";
    private TextView sendText;
    private ImageView backImage;
    private UserLocal userLocal;
    private EditText momentText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_moment);
        userLocal = DataSupport.findFirst(UserLocal.class);
        sendText=(TextView) findViewById(R.id.text_send);
        backImage = (ImageView) findViewById(R.id.image_back);
        momentText = (EditText) findViewById(R.id.et_usertext);
        sendText.setOnClickListener(this);
        backImage.setOnClickListener(this);
        urls = new ArrayList<String>();
        urls.add(ADD_IMAGE);
        momentPictureAdapter = new MomentPictureAdapter(urls);
        momentPictureAdapter.setmOnItemClickLitener(new MomentPictureAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //按下最后一个按键加载数据
                if(position ==(urls.size()-1)){
                    PhotoPicker.builder()
                            .setPhotoCount(9)
                            .setShowCamera(true)
                            .setShowGif(false)
                            .setPreviewEnabled(false)
                            .start(EditMomentActivity.this, PhotoPicker.REQUEST_CODE);
                    }

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        pictureRecyler=(RecyclerView) findViewById(R.id.recycler_picture);
        pictureRecyler.setLayoutManager(new GridLayoutManager(this,4));
        pictureRecyler.setAdapter(momentPictureAdapter);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                final ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                urls.remove(urls.size()-1);
                urls.addAll(photos);
                urls.add(ADD_IMAGE);
                momentPictureAdapter.notifyDataSetChanged();





            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.image_back:{
                onBackPressed();
            }break;
            case R.id.text_send:{
                final Moment moment = new Moment();
                moment.setUserId(userLocal.getObjectId());
                moment.setMomentText(momentText.getText().toString());
                /*List<BmobFile> bmobFiles = new ArrayList<BmobFile>();
                for(int i = 0;i<urls.size()-1;i++){
                    bmobFiles.add(new BmobFile(new File(urls.get(i))));
                }*/
                final String[] paths = new String[urls.size()-1];
                for(int i=0;i<urls.size()-1;i++){
                    paths[i] = urls.get(i);
                }
                //上传文件
                final ProgressDialog progress = new ProgressDialog(this);
                progress.setMessage("");
                progress.setCancelable(false);
                progress.setTitle("请稍后");
                progress.show();
                Bmob.uploadBatch(paths, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        if(list1.size()==paths.length){
                            //如果上传成功
                            moment.setPhotos(list);
                            moment.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        new MomentModel().save2Local(moment);
                                        Toast.makeText(EditMomentActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(EditMomentActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        progress.dismiss();
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {

                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.d("上传失败", "onError: "+s);

                    }
                });





            }break;
        }
    }
}

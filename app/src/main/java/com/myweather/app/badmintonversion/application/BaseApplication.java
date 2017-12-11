package com.myweather.app.badmintonversion.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.NineGridView;
import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.UserLocal;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;


/**
 * Created by zyt on 2017/10/14.
 */

public class BaseApplication extends Application {
    public static Context getmContext() {
        return sContext;
    }

    static Context sContext;
    private static  UserLocal userLocal;


    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();
        LitePal.initialize(this);
        NineGridView.setImageLoader(new GlideImageLoader());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static UserLocal getUserLocal(){

       UserLocal userLocal =  DataSupport.findFirst(UserLocal.class);
        if(userLocal!=null){
            return userLocal;
        }
        return null;
    }




    private class GlideImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {

            Glide.with(context).load(url)//
                   // .placeholder(R.color.cadetblue)//
                    //.error(R.color.cadetblue)//
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }




}

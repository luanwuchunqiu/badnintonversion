package com.myweather.app.badmintonversion.entity;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by zyt on 2017/10/18.
 */

public class Moment extends BmobObject implements Serializable{
    //通过绑定用户id可以得到用户名和用户头像
    private String userId;
    private String momentText;
    private List<BmobFile> photos;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMomentText() {
        return momentText;
    }

    public void setMomentText(String momentText) {
        this.momentText = momentText;
    }

    public List<BmobFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<BmobFile> photos) {
        this.photos = photos;
    }


}

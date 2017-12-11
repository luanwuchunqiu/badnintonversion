package com.myweather.app.badmintonversion.entity;


import com.myweather.app.badmintonversion.view.nine.Image;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by zyt on 2017/10/11.
 */

public class UserMoment extends BmobObject implements Serializable {
    private String userId;
    private String userName;
    private int headImageId;
    private String momentText;
    private List<Image> momentImages;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHeadImageId() {
        return headImageId;
    }

    public void setHeadImageId(int headImageId) {
        this.headImageId = headImageId;
    }

    public String getMomentText() {
        return momentText;
    }

    public void setMomentText(String momentText) {
        this.momentText = momentText;
    }

    public List<Image> getMomentImages() {
        return momentImages;
    }

    public void setMomentImages(List<Image> momentImages) {
        this.momentImages = momentImages;
    }

    public UserMoment(String userName, int headImageId, String momentText, List<Image> momentImages) {
        this.userName = userName;
        this.headImageId = headImageId;
        this.momentText = momentText;
        this.momentImages = momentImages;
    }
}

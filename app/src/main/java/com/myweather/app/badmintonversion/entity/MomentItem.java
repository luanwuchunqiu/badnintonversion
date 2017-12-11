package com.myweather.app.badmintonversion.entity;

import java.util.List;

/**
 * Created by zyt on 2017/11/27.
 */
public class MomentItem {
    private String userPhotoUrl;
    private String username;
    private String content;
    private List<String> photosUrls;

    public MomentItem(String userPhotoUrl, String username, String content, List<String> photosUrls) {
        this.userPhotoUrl = userPhotoUrl;
        this.username = username;
        this.content = content;
        this.photosUrls = photosUrls;
    }

    public MomentItem() {
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }


    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPhotosUrls() {
        return photosUrls;
    }

    public void setPhotosUrls(List<String> photosUrls) {
        this.photosUrls = photosUrls;
    }
}
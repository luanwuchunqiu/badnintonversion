package com.myweather.app.badmintonversion.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyt on 2017/10/18.
 */

public class MomentLocal extends DataSupport implements Serializable {
    private String objectId;
    private String userId;
    private String momentText;
    private List<String> photos;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}

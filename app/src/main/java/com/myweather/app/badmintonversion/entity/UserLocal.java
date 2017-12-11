package com.myweather.app.badmintonversion.entity;


import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 作者：GXL on 2016/8/3 0003
 * 博客: http://blog.csdn.net/u014316462
 * 作用：本地数据库存储的登录对象
 */

public class UserLocal extends DataSupport implements Serializable {

    //对象Id
    @Column(unique = true, defaultValue = "unknown")
    String objectId;
    //姓名

    String username;
    //头像

    String userPhoto;

    //密码

    String password;
    //手机号码

    String userAccount;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAccount() {
        return userAccount;
    }


    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}

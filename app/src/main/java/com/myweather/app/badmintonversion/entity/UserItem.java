package com.myweather.app.badmintonversion.entity;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by zyt on 2017/11/7.
 */

public class UserItem implements Comparable<UserItem>{
    private String userPhoto;

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    private String userName;
    private String status;
    private String address;
    private String userData;
    public UserItem(){}
    public UserItem(String userName, String status, String address, String userData) {
        this.userName = userName;
        this.status = status;
        this.address = address;
        this.userData = userData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }


    @Override
    public int compareTo(@NonNull UserItem o) {

            double thisData = Double.parseDouble(this.userData);
            double oData = Double.parseDouble(o.userData);
            if(thisData>oData){return 1;}
            else if(oData<thisData){return -1;}
            else {return 0;}



    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserItem userItem = (UserItem) o;

        if (userPhoto != null ? !userPhoto.equals(userItem.userPhoto) : userItem.userPhoto != null)
            return false;
        if (userName != null ? !userName.equals(userItem.userName) : userItem.userName != null)
            return false;
        if (status != null ? !status.equals(userItem.status) : userItem.status != null)
            return false;
        if (address != null ? !address.equals(userItem.address) : userItem.address != null)
            return false;
        return userData != null ? userData.equals(userItem.userData) : userItem.userData == null;

    }

    @Override
    public int hashCode() {
        int result = userPhoto != null ? userPhoto.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (userData != null ? userData.hashCode() : 0);
        return result;
    }
}

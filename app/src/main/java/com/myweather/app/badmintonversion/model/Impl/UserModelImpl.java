package com.myweather.app.badmintonversion.model.Impl;

/**
 * Created by zyt on 2017/10/14.
 */

public interface UserModelImpl {
    void getUser(String name, String passoword, BaseListener listener);
    void getUser(String objectId, final BaseListener listener);
}

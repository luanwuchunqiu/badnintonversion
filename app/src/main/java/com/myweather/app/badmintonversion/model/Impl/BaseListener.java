package com.myweather.app.badmintonversion.model.Impl;

/**
 * Created by zyt on 2017/10/14.
 */

public interface BaseListener<T> {
    void getSuccess(T t);
    void getFailure(Exception e);
}

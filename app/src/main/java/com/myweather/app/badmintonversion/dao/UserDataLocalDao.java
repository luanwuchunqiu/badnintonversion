package com.myweather.app.badmintonversion.dao;

import com.myweather.app.badmintonversion.entity.UserData;

/**
 * Created by zyt on 2017/10/26.
 */

public interface UserDataLocalDao {
    void save(String userId,String date,String data,String thisWeek,String thisMonth,String allData);
    void saveOrUpdate(UserData userData);
    void deleteAll();
}

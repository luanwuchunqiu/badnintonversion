package com.myweather.app.badmintonversion.dao;

import com.myweather.app.badmintonversion.entity.UserData;
import com.myweather.app.badmintonversion.entity.UserDataLocal;

import org.litepal.crud.DataSupport;

/**
 * Created by zyt on 2017/10/26.
 */

public class UserDataLocalDaoImpl implements UserDataLocalDao {
    //将登陆用户的信息保存到本地


    @Override
    public void save(String userId, String date, String data, String thisWeek, String thisMonth, String allData) {
        UserDataLocal userDataLocal = new UserDataLocal();
        userDataLocal.setUserId(userId);
        userDataLocal.setDate(date);
        userDataLocal.setData(data);
        userDataLocal.setThisWeekData(thisWeek);
        userDataLocal.setThisMonthData(thisMonth);
        userDataLocal.setAllData(allData);
        userDataLocal.save();
    }

    @Override
    public void saveOrUpdate(UserData userData) {


    }

    @Override
    public void deleteAll() {
        DataSupport.deleteAll(UserDataLocal.class);

    }


}

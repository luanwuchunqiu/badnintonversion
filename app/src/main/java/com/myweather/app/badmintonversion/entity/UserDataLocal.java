package com.myweather.app.badmintonversion.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyt on 2017/10/26.
 */
/**
 * 平常的数据库中是不能存储数组结构的
 *
* */

/**
 *
 *数据库的第一次修改将thisWeekData,thisMonthData,allData分为三个数据方便以后排序
* */
public class UserDataLocal extends DataSupport implements Serializable {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




    //data数据中保存当天的日期和当天的数据
    //数据格式为[当天日期,平挡,平抽,挑球,高远,扣杀,挥拍次数,运动时间,卡路里,最大拍速]
    private String data;
    private String userId;
    private String thisWeekData;
    private String thisMonthData;
    private String allData;



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getThisWeekData() {
        return thisWeekData;
    }

    public void setThisWeekData(String thisWeekData) {
        this.thisWeekData = thisWeekData;
    }

    public String getThisMonthData() {
        return thisMonthData;
    }

    public void setThisMonthData(String thisMonthData) {
        this.thisMonthData = thisMonthData;
    }

    public String getAllData() {
        return allData;
    }

    public void setAllData(String allData) {
        this.allData = allData;
    }
}

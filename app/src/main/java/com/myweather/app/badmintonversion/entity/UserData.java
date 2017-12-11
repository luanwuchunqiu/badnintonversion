package com.myweather.app.badmintonversion.entity;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by zyt on 2017/10/25.
 */

public class UserData extends BmobObject implements Serializable {
    //定义数据规范,每个用户有条记录
    //String[] 中包含的数据有日期，和对应日期的记录

    private List<List<String>> data;
    private String userId;

    private List<String> thisWeekData;
    private List<String> thisMonthData;
    private List<String> allData;
    /**
     * 数据库的第一次更改，将thisWeekData,thisMonthData,allData分别分为三个数据，以方便排序查询
     * 并将其转换为Integer类型 方便排序
     * */
    private Integer thisWeekData4Count;
    private Integer thisWeekData4Cal;
    private Double thisWeekData4Time;

    private Integer thisMonthData4Count;
    private Integer thisMonthData4Cal;
    private Double thisMonthData4Time;

    private Integer allData4Count;
    private Integer allData4Cal;
    private Double allData4Time;

    public Integer getThisWeekData4Count() {
        return thisWeekData4Count;
    }

    public void setThisWeekData4Count(Integer thisWeekData4Count) {
        this.thisWeekData4Count = thisWeekData4Count;
    }

    public Integer getThisWeekData4Cal() {
        return thisWeekData4Cal;
    }

    public void setThisWeekData4Cal(Integer thisWeekData4Cal) {
        this.thisWeekData4Cal = thisWeekData4Cal;
    }

    public Double getThisWeekData4Time() {
        return thisWeekData4Time;
    }

    public void setThisWeekData4Time(Double thisWeekData4Time) {
        this.thisWeekData4Time = thisWeekData4Time;
    }

    public Integer getThisMonthData4Count() {
        return thisMonthData4Count;
    }

    public void setThisMonthData4Count(Integer thisMonthData4Count) {
        this.thisMonthData4Count = thisMonthData4Count;
    }

    public Integer getThisMonthData4Cal() {
        return thisMonthData4Cal;
    }

    public void setThisMonthData4Cal(Integer thisMonthData4Cal) {
        this.thisMonthData4Cal = thisMonthData4Cal;
    }

    public Double getThisMonthData4Time() {
        return thisMonthData4Time;
    }

    public void setThisMonthData4Time(Double thisMonthData4Time) {
        this.thisMonthData4Time = thisMonthData4Time;
    }

    public Integer getAllData4Count() {
        return allData4Count;
    }

    public void setAllData4Count(Integer allData4Count) {
        this.allData4Count = allData4Count;
    }

    public Integer getAllData4Cal() {
        return allData4Cal;
    }

    public void setAllData4Cal(Integer allData4Cal) {
        this.allData4Cal = allData4Cal;
    }

    public Double getAllData4Time() {
        return allData4Time;
    }

    public void setAllData4Time(Double allData4Time) {
        this.allData4Time = allData4Time;
    }

    public List<String> getThisWeekData() {
        return thisWeekData;
    }

    public void setThisWeekData(List<String> thisWeekData) {
        this.thisWeekData = thisWeekData;
    }

    public List<String> getThisMonthData() {
        return thisMonthData;
    }

    public void setThisMonthData(List<String> thisMonthData) {
        this.thisMonthData = thisMonthData;
    }

    public List<String> getAllData() {
        return allData;
    }

    public void setAllData(List<String> allData) {
        this.allData = allData;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

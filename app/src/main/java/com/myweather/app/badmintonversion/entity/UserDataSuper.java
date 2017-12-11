package com.myweather.app.badmintonversion.entity;

/**
 * Created by zyt on 2017/11/13.
 */

/**
 * 最终呈现给用户的数据
 * 包括本周数据
 * 和今天的数据
 */
public class UserDataSuper {
    //该数据是周几的数据
    private String week;
    //该数据所属的日期
    private String date;
    //一天中全部的数据
    private String data;
   public UserDataSuper(){}
    public UserDataSuper(String week, String date, String data) {
        this.week = week;
        this.date = date;
        this.data = data;
    }

    private Integer thisWeekData4Count;
    private Integer thisWeekData4Cal;
    private Double thisWeekData4Time;

    private Integer thisMonthData4Count;
    private Integer thisMonthData4Cal;
    private Double thisMonthData4Time;

    private Integer allData4Count;
    private Integer allData4Cal;
    private Double allData4Time;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
}

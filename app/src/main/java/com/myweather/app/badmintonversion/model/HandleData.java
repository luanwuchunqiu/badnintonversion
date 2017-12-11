package com.myweather.app.badmintonversion.model;

/**
 * Created by zyt on 2017/10/23.
 */

public class HandleData implements Runnable {
    private DataBuffer dataBuffer;
    public HandleData(DataBuffer dataBuffer){
        this.dataBuffer = dataBuffer;
    }
    @Override
    public void run() {
        dataBuffer.handleData();
    }
}

package com.myweather.app.badmintonversion.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.myweather.app.badmintonversion.utils.Statistics;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zyt on 2017/10/23.
 */

public class DataBuffer {

    private Context mContext;
    public static final String RESULT_DATA = "result_data";
    public static final String SEND_RESULT = "send_result";
    private ArrayList<double[]> datas = new ArrayList<double[]>();

    private int[] result = new int[5];

    /*private Handler handler;
    public DataBuffer(Handler handler){
        this.handler = handler;

    }*/

    public DataBuffer(Context mContext){
        this.mContext = mContext;
    }

    //接收数据数据
    public void receiveData(double[] data){
        datas.add(data);
        if(datas.size()>=800){
            datas.clear();
        }

    }

    //处理数据
    public void handleData(){

        int n = datas.size()-80;//截取数据的初始位置
        ArrayList<double []> newData = new ArrayList<double[]>();
        ArrayList<double[]> windowDatas = new ArrayList<double[]>();
        for(int ii = n;ii<datas.size();ii++){
            newData.add(datas.get(ii));

        }


        double[][] arrayNewData = newData.toArray(new double[newData.size()][]);

        double[][] arrayNewDataT = getTranspose(arrayNewData);//矩阵的转置

        int j = 0;
        double thePoint = Statistics.getMax(arrayNewDataT[0]);

        Log.d("击球", "handle: "+thePoint+"\t"+arrayNewDataT[0].length);

        //判断击球点判断的依据是x轴的最大加速度，当x轴的加速度大于4时即视为是一次击球动作。
        if((Statistics.getMax(arrayNewDataT[0])>=2.5)){//2.5
            j= Arrays.binarySearch(arrayNewDataT[0],Statistics.getMax(arrayNewDataT[0]));






        }

        if(j!=0)
        {
            if (j < 20) {

                for (int i = 0; i <  40; i++) {
                    windowDatas.add(newData.get(i));
                }
            } else if ((j >=  20) && (j < 60)) {
                for (int i = j - 20; i < j + 20; i++) {
                    windowDatas.add(newData.get(i));
                }
            } else if (j >=  60) {

                for (int i = 40; i < 80; i++) {
                    windowDatas.add(newData.get(i));
                }
            }
            double[][] windowDatasArray = windowDatas.toArray(new double[windowDatas.size()][]);
            double[][] selectedDatas = getTranspose(windowDatasArray);//得到一个窗口的数据  数据的格式为6*40


            //判断是什么动作

//11, 1768

            //1. 先用均方根区分出高远，扣杀与其他动作的区别，区别的依据是均方根。
            if (Statistics.getMeanSquareRoot(selectedDatas[0]) > 3.1) {//3.1
                //2. 区分高远和扣杀，判断的依据为运动强度的平均值
                if (Statistics.getAverage(Statistics.getMovementIntensity(windowDatasArray)) > 3.5) {//4.0
                    result[4] += 1;//扣杀

                } else if (Statistics.getAverage(Statistics.getMovementIntensity(windowDatasArray)) <= 3.5) {
                    result[3] += 1;//高远

                }


            }
            //多线程之间的通信，先采集数据，然后进行数据的分析最后将分析的结果传到UI线程，进行显示，实现数据的并行处理
            else if (Statistics.getMeanSquareRoot(selectedDatas[0]) <= 3.1) {
                //3.判断平挡，平抽和挑球 判断的依据是y轴的中位数  为了更精确尽量使用别的判断标准
                if (Statistics.getAverage(selectedDatas[1]) < 0.49) {
                    result[0] += 1;//平挡
                } else if (Statistics.getAverage(selectedDatas[1]) >= 0.49) {
                    if (Statistics.getMax(selectedDatas[4]) > 610) {
                        result[1] += 1;//平抽
                    } else {
                        result[2] += 1;

                    }//挑球

                }
            }
        }
        newData.clear();
        //把包含结果的广播放送出去
        broadcastUpdate(mContext,SEND_RESULT,result);
        /*Message message = new Message();
        message.obj = result;
        //传送数据
        handler.sendMessage(message);*/


    }


    public double[][] getTranspose(double[][] data){
        double a[][] = new double[data[0].length][data.length];
        for(int i = 0;i < data[0].length;i++)
        {
            for(int j = 0;j < data.length;j++)
            {
                a[i][j] = data[j][i];
            }
        }
        return a;
    }


    private void broadcastUpdate(final Context mContext,final String action,
                                 final int[] result) {
        final Intent intent = new Intent(action);
        intent.putExtra(RESULT_DATA, result);
        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    public ArrayList<double[]> getDatas() {
        return datas;
    }
}

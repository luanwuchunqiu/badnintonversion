package com.myweather.app.badmintonversion.utils;

/**
 * Created by ZYT on 2017/6/7.
 */




import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.Arrays;


public class Statistics {


    /**
     * 求给定双精度数组中值的最大值
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果,如果输入值不合法，返回为-1
     */
    public static double getMax(double[] inputData) {
        if (inputData == null || inputData.length == 0)
            return -1;
        int len = inputData.length;
        double max = inputData[0];
        for (int i = 0; i < len; i++) {
            if (max < inputData[i])
                max = inputData[i];
        }
        return max;
    }

    /**
     * 求求给定双精度数组中值的最小值
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果,如果输入值不合法，返回为-1
     */
    public static double getMin(double[] inputData) {
        if (inputData == null || inputData.length == 0)
            return -1;
        int len = inputData.length;
        double min = inputData[0];
        for (int i = 0; i < len; i++) {
            if (min > inputData[i])
                min = inputData[i];
        }
        return min;
    }







    /**
     * 求给定双精度数组中值的和
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static double getSum(double[] inputData) {
        if (inputData == null || inputData.length == 0)
            return -1;
        int len = inputData.length;
        double sum = 0;
        for (int i = 0; i < len; i++) {
            sum = sum + inputData[i];
        }

        return sum;

    }

    public static double getSum(double[] inputData,int a,int b){
        if (inputData == null || inputData.length == 0)
            return -1;
        double sum = 0;
        for (int i = a; i <b; i++) {
            sum = sum + inputData[i];
        }

        return sum;


    }

    /**
     * 求给定双精度数组中值的数目
     *
     * @param inputData 输入数据数组
     * @return 运算结果
     */
    public static int getCount(double[] inputData) {
        if (inputData == null)
            return -1;

        return inputData.length;
    }

    /**
     * 求给定双精度数组中值的平均值
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static double getAverage(double[] inputData) {
        if (inputData == null || inputData.length == 0)
            return -1;
        int len = inputData.length;
        double result;
        result = getSum(inputData) / len;

        return result;
    }


    public static double getAverage(double[] inputData,int a,int b){
        if (inputData == null || inputData.length == 0)
            return -1;
        //int len = inputData.length;
        double result;
        result = getSum(inputData,a,b) / (b-a);

        return result;
    }

    /**
     * 求给定双精度数组中值的平方和
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static double getSquareSum(double[] inputData) {
        if(inputData==null||inputData.length==0)
            return -1;
        int len=inputData.length;
        double sqrsum = 0.0;
        for (int i = 0; i <len; i++) {
            sqrsum = sqrsum + inputData[i] * inputData[i];
        }


        return sqrsum;
    }

    public static double getSquareSum(double[] inputData,int a,int b)
    {
        if(inputData==null||inputData.length==0)
            return -1;
        double sqrsum = 0.0;
        for (int i = a; i < b; i++) {
            sqrsum = sqrsum + inputData[i] * inputData[i];}
        return sqrsum;

    }



    /**
     * 求给定双精度数组中值的方差
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static double getVariance(double[] inputData) {
        int count = getCount(inputData);
        double sqrsum = getSquareSum(inputData);
        double average = getAverage(inputData);
        double result;
        result = (sqrsum - count * average * average) / count;

        return result;
    }


    public static double getVariance(double[] inputData,int a,int b){
        double sqrsum = getSquareSum(inputData,a,b);
        double average = getAverage(inputData,a,b);
        double result;
        result = (sqrsum - (b-a) * average * average) / (b-a);

        return result;
    }

    /**
     * 求给定双精度数组中值的标准差
     *
     * @param inputData
     *            输入数据数组
     * @return 运算结果
     */
    public static double getStandardDiviation(double[] inputData) {
        double result;
        //绝对值化很重要
        result = Math.sqrt(Math.abs(getVariance(inputData)));

        return result;

    }



    /**
     * 取出二维数组的一部分
     * 按照行
     *
     *
     * */

    public static double[][] getMetrix(double[][] f,int a,int b)
    {
        if(a<0||b>f.length){
            throw new RuntimeException("超出范围");
        }
        double[][] f1=new double[b-a][];
        for(int i=0,j=a;i<b-a;i++,j++)
        {
            f1[i] = f[j];

        }
        return f1;
    }

    /**
     * 取出二维数组的一部分
     * 按照列
     *
     *
     * */

    public static double[][] getMetrixByColumn(double[][] f,int a,int b)
    {   double[][] result = new double[f.length][];
        for (int i = 0; i < f.length; i++) {
            if((a<0)||b>f[i].length) throw new RuntimeException("超出范围");

            result[i] = Arrays.copyOfRange(f[i], a, b);
        }
        return result;
    }









    /**
     *
     * 得到滑动方差
     *
     * */

    public static double getMeanacc(double x,double y,double z)
    {
        return  Math.sqrt(x*x+y*y+z*z);
    }






    /**
     *
     * 得到矩阵的均方差
     *
     * */
    public static double getMeanSquareRoot(double[] data){


        return Math.sqrt(getSquareSum(data)/data.length);//这里应该除以 data.length


    }

    /**
     *
     * 得到 数组的skewness 偏度
     *
     * */

    public static double getSkewness(double[] data){
        double numerator = 0,denominator=0,skewness=0;
        for (int i = 0; i < data.length; i++) {

            numerator = Math.pow((data[i]-getAverage(data)), 3)+numerator;

            denominator = Math.pow((data[i]-getAverage(data)), 2)+denominator;
        }
        numerator = numerator/data.length;
        denominator = Math.pow((Math.sqrt(denominator/data.length)),3);
        skewness = numerator/denominator;
        return skewness;



    }


    /**
     *
     * 得到 数组的Kurtosis  峰值
     *
     * */
    public static double getKurtosis(double[] data){
        double numerator = 0,denominator=0,kurtosis=0;
        for (int i = 0; i < data.length; i++) {

            numerator = Math.pow((data[i]-getAverage(data)), 4)+numerator;

            denominator = Math.pow((data[i]-getAverage(data)), 2)+denominator;
        }
        numerator = numerator/data.length;
        denominator = Math.pow((denominator/data.length),2);
        kurtosis = numerator/denominator;
        return kurtosis;
    }

    /**
     *
     * 得到 数组的绝对均方差
     *
     * */
    public static double getMeanAbsoluteDeviation(double[] data){
        double mad = 0;
        if (data == null || data.length == 0)
            return -1;
        for (int i = 0; i < data.length; i++) {
            mad = Math.abs(data[i]-getAverage(data))+mad;
        }
        mad = Math.sqrt(mad/2);//此处有问题 本应该除以( data.length-1)
        return mad;

    }


    /**
     *
     * 得到 数组信号的信号幅度
     *
     * */
    public static double getSignalMagnitudeArea(double[][] data){
        double sma = 0;
        for (int i = 0; i < data.length; i++) {
            for(int j = 0; j <data[i].length;j++){
                sma = Math.abs(data[i][j]) + sma;

            }

        }
        sma = sma/data[0].length;
        return sma;
    }


    /**
     *
     * 得到 数组信号的运动强度
     *
     * */
    public static double[] getMovementIntensity(double[][] data){
        double[] sma = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            for(int j = 0; j <data[i].length;j++){
                sma[i] = Math.pow((data[i][j]), 2) + sma[i];

            }
            sma[i] = Math.sqrt(sma[i]);

        }

        return sma;
    }
    /**
     *
     * 得到 数组信号的快速傅里叶
     *
     * */
    public static Complex[] getFastFourierTransformer(double[] inputData) throws Exception
    {
        FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] result = fft.transform(inputData, TransformType.FORWARD);
        return result;
    }


    /**
     *
     * 得到 数组信号的能量值
     *
     * */


    public static double getEnergy(double[] inputData) throws Exception{
        double energy = 0;
        Complex[] complexs = getFastFourierTransformer(inputData);
        for (int i = 0; i < complexs.length; i++) {
            energy = Math.pow(complexs[i].abs(), 2)+energy;
        }
        energy = energy/inputData.length;
        return energy;


    }


    /**
     *
     * 得到 矩阵数据的归一化
     *
     * */

    public static double[][] getNormalization(double[][] data){
        double[][] reData = new double[data.length][data[0].length];
        double[] mean = new double[data.length];
        double[] var = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            mean[i] = getAverage(data[i]);
            var[i] = getVariance(data[i])*data[i].length/(data[i].length-1);
            for (int j = 0; j < data[i].length; j++) {
                reData[i][j] = (data[i][j]-mean[i])/Math.sqrt(var[i]);
            }

        }
        return reData;

    }




    /**
     * 求给定双精度数组中值的中位数
     *
     * @param data
     *            输入数据数组
     * @return 运算结果,如果输入值不合法，返回为-1
     */

    public static double getMedian(double[] data){
       int n = data.length;
        double m = 0;
       Arrays.sort(data);
       if(n%2==0){
           m=(data[n/2]+data[n/2-1])/2;
       }
        else if(n%2!=0){
          m= data[n/2];
       }
        return m;
    }


    public static void main(String[] args) throws Exception{//测试工具包中的函数

       /* File file=new File("C:\\Users\\ZYT\\Desktop\\traindata.mat");

        Matrix testMatrix = ImportMatrixMAT.fromFile(file);//读取Mat文件

        testMatrix = testMatrix.deleteRows(Ret.NEW, testMatrix.getRowCount()-1);//删除行
        testMatrix = testMatrix.transpose();//矩阵转置

        double[][] test2 = getNormalization(testMatrix.toDoubleArray());
        testMatrix = Matrix.Factory.importFromArray(test2);
        testMatrix.showGUI();
        System.out.println("ss");

        double[][] test1= {{1.1,1.2,1.3,1.4,1.5,1.6},
                {2.1,2.2,2.3,2.4,2.5,2.6},
                {3.1,3.2,3.3,3.4,3.5,3.6},
                {4.1,4.2,4.4,2.4,4.5,4.6},
                {5.1,5.2,5.5,2.4,5.5,5.6}

        };
        printMetrix(getNormalization(test1));*/

    }

    public static void printMetrix(double[][] test){
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[i].length; j++) {
                System.out.print(test[i][j]+"\t");

            }
            System.out.println();

        }
    }

}
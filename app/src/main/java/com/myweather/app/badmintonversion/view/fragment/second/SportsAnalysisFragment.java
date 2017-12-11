package com.myweather.app.badmintonversion.view.fragment.second;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.application.BaseApplication;
import com.myweather.app.badmintonversion.entity.UserDataSuper;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserDataService;

/**
 * Created by zyt on 2017/8/25.
 */

public class SportsAnalysisFragment extends Fragment{
    private static final String[] date = {"周一","周二","周三","周四","周五","周六","周日"};

    private LineChart lineChart;
    private TextView count1TextView;
    private TextView count2TextView;


    private static final String ARG_SECTION_NUMBER = "section_number";
    public SportsAnalysisFragment() {
    }

    String[][] titles = {{"周挥拍次数","累计挥拍次数"},{"周运动时间","累计运动时间"},{"周消耗卡里路","累计消耗卡路里"},{"周最大拍速","历史最大速度"}};
    String[][] counts = {{"0","0"},{"0.0","0.0"},{"0","0"},{"0","0"}};
    String[][] measures = {{"次","次"},{"h","h"},{"Cal","Cal"},{"km/h","km/h"}};
    String [] descriptions = {"挥拍次数(次)","运动时间(h)","消耗卡里路(Cal)","最大拍速(Km/h)"};

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SportsAnalysisFragment newInstance(int sectionNumber) {
        SportsAnalysisFragment fragment = new SportsAnalysisFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sports_analysis, container, false);



        TextView title1TextView = (TextView) rootView.findViewById(R.id.sports_analysis_title1);
        TextView title2TextView = (TextView) rootView.findViewById(R.id.sports_analysis_title2);
        count1TextView = (TextView) rootView.findViewById(R.id.sports_analysis_count1);
        count2TextView = (TextView) rootView.findViewById(R.id.sports_analysis_count2);
        TextView measure1TextView = (TextView) rootView.findViewById(R.id.sports_analysis_measure1);
        TextView measure2TextView = (TextView) rootView.findViewById(R.id.sports_analysis_measure2);
        TextView descriptionTextView =(TextView) rootView.findViewById(R.id.sports_analysis_chart_description);

        title1TextView.setText(titles[this.getArguments().getInt(ARG_SECTION_NUMBER)-1][0]);
        title2TextView.setText(titles[this.getArguments().getInt(ARG_SECTION_NUMBER)-1][1]);
        count1TextView.setText(counts[this.getArguments().getInt(ARG_SECTION_NUMBER)-1][0]);
        count2TextView.setText(counts[this.getArguments().getInt(ARG_SECTION_NUMBER)-1][1]);
        measure1TextView.setText(measures[this.getArguments().getInt(ARG_SECTION_NUMBER)-1][0]);
        measure2TextView.setText(measures[this.getArguments().getInt(ARG_SECTION_NUMBER)-1][1]);
        descriptionTextView.setText(descriptions[this.getArguments().getInt(ARG_SECTION_NUMBER)-1]);

         lineChart = (LineChart) rootView.findViewById(R.id.line_chart);
        initLineChart(lineChart,this.getArguments().getInt(ARG_SECTION_NUMBER));




        return rootView;
    }


    private void initLineChart(LineChart lineChart,int position){
        lineChart.setScaleEnabled(false);
        lineChart.animateXY(1000,1000);

        lineChart.getDescription().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisMaxValue(6F);
        xAxis.setLabelCount(6);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return date[(int)value%7];
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(R.color.white));
        xAxis.setDrawGridLines(false);

        lineChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setMinWidth(2f);
        leftAxis.setLabelCount(6,true);
        leftAxis.setTextColor(getResources().getColor(R.color.white));
        leftAxis.setGridColor(getResources().getColor(R.color.white));
        leftAxis.setDrawAxisLine(false);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        lineChart.setData(data);
        addData2LineChart(lineChart,0,0,position);
        addData2LineChart(lineChart,1,0,position);
        addData2LineChart(lineChart,2,0,position);
        addData2LineChart(lineChart,3,0,position);
        addData2LineChart(lineChart,4,0,position);
        addData2LineChart(lineChart,5,0,position);
        addData2LineChart(lineChart,6,0,position);




    }
    public void refreshLineChart(int[] lineData){
        int position = this.getArguments().getInt(ARG_SECTION_NUMBER);
       /* for(int i=0;i<7;i++){
            addData2LineChart(lineChart,i,lineData[i],position);
        }*/
        //addData2LineChart(lineChart,i,lineData[i],position);
        LineData data = lineChart.getLineData();
        data.clearValues();
        addData2LineChart(lineChart,0,lineData[0],position);
        addData2LineChart(lineChart,1,lineData[1],position);
        addData2LineChart(lineChart,2,lineData[2],position);
        addData2LineChart(lineChart,3,lineData[3],position);
        addData2LineChart(lineChart,4,lineData[4],position);
        addData2LineChart(lineChart,5,lineData[5],position);
        addData2LineChart(lineChart,6,lineData[6],position);

    }
    public void refreshTextData(String textData1,String textData2){
        count1TextView.setText(textData1);
        count2TextView.setText(textData2);
            }
    public void refreshLineChart(double[] lineData){
        int position = this.getArguments().getInt(ARG_SECTION_NUMBER);
       /* for(int i=0;i<7;i++){
            addData2LineChart(lineChart,i,lineData[i],position);
        }*/
        //addData2LineChart(lineChart,i,lineData[i],position);
        LineData data = lineChart.getLineData();
        data.clearValues();
        addData2LineChart(lineChart,0,(float) lineData[0],position);
        addData2LineChart(lineChart,1,(float)lineData[1],position);
        addData2LineChart(lineChart,2,(float)lineData[2],position);
        addData2LineChart(lineChart,3,(float)lineData[3],position);
        addData2LineChart(lineChart,4,(float)lineData[4],position);
        addData2LineChart(lineChart,5,(float)lineData[5],position);
        addData2LineChart(lineChart,6,(float)lineData[6],position);

    }






    private void addData2LineChart(LineChart lineChart,int dataX,float dataY,int position){

        LineData data = lineChart.getData();
        data.setHighlightEnabled(true);
        if(data!=null){
            LineDataSet set = ( LineDataSet)data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();

                data.addDataSet(set);
                if(position==1){
                data.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                        return ""+(int)value;
                    }
                });}
            }
             lineChart.getLegend().setEnabled(false);//使标签失效

            data.addEntry(new Entry(dataX, dataY), 0);

            data.notifyDataChanged();

            // let the chart know it's data has changed
            lineChart.notifyDataSetChanged();
        }




    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(getResources().getColor(R.color.orange));
        set.setCircleColor(getResources().getColor(R.color.orange));
        set.setLineWidth(1f);
        set.setCircleRadius(4f);
        set.setCircleColorHole(getResources().getColor(R.color.white));
        set.setCircleHoleRadius(2f);


        set.setFillAlpha(30);
        set.setFillColor(getResources().getColor(R.color.orange));
        set.setDrawFilled(true);
        set.setHighLightColor(getResources().getColor(R.color.orange));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        return set;
    }
}

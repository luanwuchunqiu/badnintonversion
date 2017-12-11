package com.myweather.app.badmintonversion.view.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.application.BaseApplication;
import com.myweather.app.badmintonversion.entity.UserData;
import com.myweather.app.badmintonversion.entity.UserDataSuper;
import com.myweather.app.badmintonversion.entity.UserLocal;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserDataService;
import com.myweather.app.badmintonversion.view.adapter.SportsAnalysisViewPaperAdapter;
import com.myweather.app.badmintonversion.view.fragment.second.SportsAnalysisFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zyt on 2017/10/16.
 */

public class SportsAnalysisActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    private PieChart mChart;
    private ViewPager viewPager;
    private SportsAnalysisViewPaperAdapter adapter;
    private UserDataService userDataService;
    private ImageView analysisIndicator0;
    private ImageView analysisIndicator1;
    private ImageView analysisIndicator2;
    private ImageView analysisIndicator3;
    private TextView analysisDate;
    private ImageView[]  indicators;
    private UserDataSuper[] userDataSupers;
    private UserLocal userLocal;
    private Handler mHandler;
    private boolean updateUiFalg;
    private SportsAnalysisFragment fragment1;
    private SportsAnalysisFragment fragment2;
    private SportsAnalysisFragment fragment3;
    private SportsAnalysisFragment fragment4;
    private UserDataSuper mUserDataSuper;

    private List<Fragment> fragments;



    protected String[] mParties = new String[] {
            "平挡","平抽","挑球","高远","扣杀"
    };

    //解析数据
    //1.五个动作的数据
    private int[] mFiveAction;
    //2.一周的挥拍次数
    private int[] mAWeekCount;
    //3.一周的卡路里
    private int[] mAWeekCal;
    //4.一周的运动时间
    private double[] mAWeekHour;

    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_analysis);
        analysisIndicator0 = (ImageView)findViewById(R.id.analysis_indicator_0) ;
        analysisIndicator1 = (ImageView)findViewById(R.id.analysis_indicator_1) ;
        analysisIndicator2 = (ImageView)findViewById(R.id.analysis_indicator_2) ;
        analysisIndicator3 = (ImageView)findViewById(R.id.analysis_indicator_3) ;
        indicators = new ImageView[]{analysisIndicator0,analysisIndicator1,analysisIndicator2,analysisIndicator3};
        analysisDate = (TextView) findViewById(R.id.analysis_date);
        userLocal = BaseApplication.getUserLocal();
        userDataService = new UserDataService();
        fragments = new ArrayList<>();
        fragment1 = SportsAnalysisFragment.newInstance(1);
        fragment2 = SportsAnalysisFragment.newInstance(2);
        fragment3 = SportsAnalysisFragment.newInstance(3);
        fragment4 = SportsAnalysisFragment.newInstance(4);
        fragments.add(fragment1);fragments.add(fragment2);fragments.add(fragment3);fragments.add(fragment4);


        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                //第一次更新ui只需要更新第一页的数据
                //设置一个标志位当第二次更新ui时就不用在此处更新
                //在这里更新ui
                switch (msg.what){
                    case 0x10:{
                        //设更新ui的标志位为真
                        updateUiFalg = true;
                        //并更新初始的页面

                        if(fragment1!=null)
                            fragment1.refreshLineChart(mAWeekCount);



                    }break;
                }
                return false;
            }
        });

        //userDataSupers = new UserDataSuper[7];
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("");
        progressDialog.setMessage("请稍后....");
        progressDialog.setCancelable(false);
        progressDialog.show();
      userDataService.getAWeekUserData(userLocal.getObjectId(), new BaseListener() {
          //得到本周数据
          @Override
          public void getSuccess(Object o) {
              userDataSupers = (UserDataSuper[]) o;
              Message message = new Message();

              message.what = 0x10;
              mFiveAction = getFiveAction(userDataSupers);
              mAWeekCount = getAweekCount(userDataSupers);
              mAWeekCal = getAweekCal(userDataSupers);
              mAWeekHour = getAweekHour(userDataSupers);
              //如果已经查询到了数据就发送消息更新ui
              mHandler.sendMessage(message);
              setData(mFiveAction);
              Log.d("Thread", "getSuccess: "+Thread.currentThread().getName());
              //progressDialog.dismiss();


              progressDialog.dismiss();
          }

          @Override
          public void getFailure(Exception e) {

              progressDialog.dismiss();
          }
      });
        //从本地数据库取数据
     mUserDataSuper =  userDataService.getRecentlyData();
        if(mUserDataSuper==null){
            userDataService.getRecentlyDataFromServer(userLocal.getObjectId(), new BaseListener() {
                @Override
                public void getSuccess(Object o) {
                    Message message = new Message();
                    message.what = 0x01;
                    mHandler.sendMessage(message);
                    mUserDataSuper = (UserDataSuper) o;
                }

                @Override
                public void getFailure(Exception e) {

                }
            });
        }

        adapter = new SportsAnalysisViewPaperAdapter(getSupportFragmentManager(),fragments);
        viewPager = (ViewPager)findViewById(R.id.viewPaper_sports_analysis);
        viewPager.setAdapter(adapter);
      //viewPager.set
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:{

                        if(updateUiFalg){
                        fragment1.refreshLineChart(mAWeekCount);
                        fragment1.refreshTextData(mUserDataSuper.getThisWeekData4Count()+"",mUserDataSuper.getAllData4Count()+"");}

                    }break;
                    case 1:{
                        if(updateUiFalg){
                        fragment2.refreshLineChart(mAWeekHour);
                            fragment2.refreshTextData(mUserDataSuper.getThisWeekData4Time()+"",mUserDataSuper.getAllData4Time()+"");}
                    }break;
                    case 2:{
                        if(updateUiFalg){
                        fragment3.refreshLineChart(mAWeekCal);
                            fragment3.refreshTextData(mUserDataSuper.getThisWeekData4Cal()+"",mUserDataSuper.getAllData4Cal()+"");
                        }
                    }break;
                    case 3:{

                    }break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Date date = new Date();
        String week = new SimpleDateFormat("EEEE").format(date);
        String localDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        switch (week){
            case "星期一":{
                analysisDate.setText("("+localDate+"~"+localDate+")");

            }break;
            case  "星期二":{



                c.set(Calendar.DATE, day - 1);

                String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                        .getTime());
                analysisDate.setText("("+dayBefore+"~"+localDate+")");
            }break;
            case "星期三":{


                c.set(Calendar.DATE, day - 2);

                String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                        .getTime());
                analysisDate.setText("("+dayBefore+"~"+localDate+")");
            }break;
            case  "星期四":{
                c.set(Calendar.DATE, day - 3);

                String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                        .getTime());
                analysisDate.setText("("+dayBefore+"~"+localDate+")");

            }break;
            case "星期五":{
                c.set(Calendar.DATE, day - 4);

                String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                        .getTime());
                analysisDate.setText("("+dayBefore+"~"+localDate+")");
            }break;
            case  "星期六":{
                c.set(Calendar.DATE, day - 5);

                String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                        .getTime());
                analysisDate.setText("("+dayBefore+"~"+localDate+")");
            }break;
            case "星期日":{
                c.set(Calendar.DATE, day - 6);

                String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                        .getTime());
                analysisDate.setText("("+dayBefore+"~"+localDate+")");
            }break;

        }



        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(2, 5, 2, 2);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText(generateCenterSpannableText());
        mChart.setDrawHoleEnabled(true);




        mChart.setHoleColor(getResources().getColor(R.color.colorDark));

        mChart.setTransparentCircleColor(getResources().getColor(R.color.colorDark2));
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(60f);
        mChart.setTransparentCircleRadius(64f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);



        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);



        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(7f);
        l.setTextColor(Color.WHITE);

        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("");

        //SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        /*s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);*/
        return s;
    }
    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }

    private void setData(int[] actionData) {



        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        for (int i = 0; i < actionData.length ; i++) {
            entries.add(new PieEntry((float) actionData[i],
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");

    }

    //得到一周的action

    /**
     * 得到一周的数据
     * 数据的格式是长度为5的数组
     * @param userDataSupers
     * @return
     */
    public int[] getFiveAction(UserDataSuper[] userDataSupers){
    int[] fiveAction = new int[5];
    for(int i = 0;i<userDataSupers.length;i++){
        if(userDataSupers[i]!=null){
        fiveAction[0]=Integer.parseInt(userDataSupers[i].getData().split(",")[1])+fiveAction[0];
            fiveAction[1]=Integer.parseInt(userDataSupers[i].getData().split(",")[2])+fiveAction[1];
            fiveAction[2]=Integer.parseInt(userDataSupers[i].getData().split(",")[3])+fiveAction[2];
            fiveAction[3]=Integer.parseInt(userDataSupers[i].getData().split(",")[4])+fiveAction[3];
            fiveAction[4]=Integer.parseInt(userDataSupers[i].getData().split(",")[5])+fiveAction[4];
        }
    }
    return fiveAction;
}
//得到一周的挥拍次数
    //数据类型为周一，周二 如果为空设为零

    /**
     *
     * @param userDataSupers
     * @return 数据为一个长度为7的数组
     */
    public int[] getAweekCount(UserDataSuper[] userDataSupers){
        int[] aWeekCount = new int[7];
        for(int i = 0;i<userDataSupers.length;i++){
            if(userDataSupers[i]==null)aWeekCount[i]=0;
            else aWeekCount[i]=Integer.parseInt(userDataSupers[i].getData().split(",")[6]);
        }
        return aWeekCount;
    }
    /**
     *
     * @param userDataSupers
     * @return 数据为一个长度为7的数组
     */
    public int[] getAweekCal(UserDataSuper[] userDataSupers){
        int[] aWeekCal = new int[7];
        for(int i = 0;i<userDataSupers.length;i++){
            if(userDataSupers[i]==null)aWeekCal[i]=0;
            else aWeekCal[i]=Integer.parseInt(userDataSupers[i].getData().split(",")[7]);
        }
        return aWeekCal;
    }


    /**
     *
     * @param userDataSupers
     * @return 数据为一个长度为7的数组
     */
    public double[] getAweekHour(UserDataSuper[] userDataSupers){
        double[] aWeekHour = new double[7];
        for(int i = 0;i<userDataSupers.length;i++){
            if(userDataSupers[i]==null)aWeekHour[i]=0;
            else aWeekHour[i]=Double.parseDouble(userDataSupers[i].getData().split(",")[8]);
        }
        return aWeekHour;
    }


   /* @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }*/
}

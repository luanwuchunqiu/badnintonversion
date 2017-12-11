package com.myweather.app.badmintonversion.view.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.MomentItem;
import com.myweather.app.badmintonversion.entity.UserMoment;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserDataService;
import com.myweather.app.badmintonversion.view.activity.EditMomentActivity;
import com.myweather.app.badmintonversion.view.adapter.MomentItemsAdapter;
import com.myweather.app.badmintonversion.view.adapter.MomentsAdapter;
import com.myweather.app.badmintonversion.view.nine.Image;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zyt on 2017/9/7.
 */

public class FriendsFragment extends Fragment implements View.OnClickListener {
    private ListView listView;
    private ImageView addMoment;
    private UserDataService userDataService;

    private List<List<Image>> imagesList;
    private List<UserMoment> userMoments;
    private List<MomentItem> momentItemList;
    private TextView textView;
    private PtrClassicFrameLayout ptr;
    private AVLoadingIndicatorView avi;


    private String[][] images=new String[][]{{
            "http://img4.duitang.com/uploads/item/201209/25/20120925201555_eUHEU.jpeg","640","960"}
            ,{"file:///android_asset/img2.jpg","250","250"}
            ,{"file:///android_asset/img3.jpg","250","250"}
            ,{"file:///android_asset/img4.jpg","250","250"}
            ,{"file:///android_asset/img5.jpg","250","250"}
            ,{"file:///android_asset/img6.jpg","250","250"}
            ,{"file:///android_asset/img7.jpg","250","250"}
            ,{"file:///android_asset/img8.jpg","250","250"}
            ,{"http://img3.douban.com/view/photo/raw/public/p1708880537.jpg","1280","800"}
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends,container,false);
        //textView = (TextView) view.findViewById(R.id.textFriend);
        listView= (ListView) view.findViewById(R.id.lv_main);
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        //下拉刷新控件
        ptr = (PtrClassicFrameLayout) view.findViewById(R.id.ptr);
        userDataService = new UserDataService();

        addMoment = (ImageView) view.findViewById(R.id.image_add_moment);
        addMoment.setOnClickListener(this);
        initData();

        ptr.setLastUpdateTimeRelateObject(this);
        //下拉刷新的控件设置刷新
        //下拉刷新时调用
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                //刷新完成
                ptr.refreshComplete();
            }
        });

        //initData();
       // listView.setAdapter(new MomentsAdapter(getActivity(),userMoments));
       // listView.setAdapter(new MomentItemsAdapter(getActivity(),momentItemList));
        return view;

    }
    //进行listView的刷新
    //后续考虑listView的性能问题

    /**
     * 刷新数据的逻辑
     * 1.按时间将各个动态排序
     * 2.每次刷新数据应该是在本有的数据上面进行添加而不是重新shu'xin
     */
    private void refreshData(){

    }

    private void initData(){
        List<String> images = new ArrayList<>();
        for(int i = 0;i<9;i++){
            images.add("https://picsum.photos/400/800/");
        }
        momentItemList = new ArrayList<>();

        avi.show();
        userDataService.getMomentList(new BaseListener() {
            @Override
            public void getSuccess(Object o) {
                momentItemList = (List<MomentItem>) o;
                Toast.makeText(getActivity(),"查询成功",Toast.LENGTH_LONG).show();
                listView.setAdapter(new MomentItemsAdapter(getActivity(),momentItemList));
                avi.hide();

            }

            @Override
            public void getFailure(Exception e) {
                Toast.makeText(getActivity(),"查询失败",Toast.LENGTH_LONG).show();
            }
        });

    }

    /*private void initData() {
        userMoments = new ArrayList<UserMoment>();


        imagesList=new ArrayList<>();
        //这里单独添加一条单条的测试数据，用来测试单张的时候横竖图片的效果
        ArrayList<Image> singleList=new ArrayList<>();
        singleList.add(new Image(images[8][0], Integer.parseInt(images[8][1]), Integer.parseInt(images[8][2])));

        userMoments.add(new UserMoment("小刚",R.drawable.img1,"内容",singleList));

        imagesList.add(singleList);
        //从一到9生成9条朋友圈内容，分别是1~9张图片
        for(int i=0;i<9;i++){
            ArrayList<Image> itemList=new ArrayList<>();
            for(int j=0;j<=i;j++){
                itemList.add(new Image(images[j][0], Integer.parseInt(images[j][1]), Integer.parseInt(images[j][2])));
            }
            imagesList.add(itemList);

            userMoments.add(new UserMoment("小红",R.drawable.img3,"内容1",itemList));
        }
    }*/
    public void fresh(String string){
//        textView.setText(string);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_add_moment:{
                startActivity(new Intent(getActivity(),EditMomentActivity.class));
            }break;

        }
    }
}

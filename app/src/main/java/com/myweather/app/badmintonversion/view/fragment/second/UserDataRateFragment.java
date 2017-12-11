package com.myweather.app.badmintonversion.view.fragment.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.UserDataItem;
import com.myweather.app.badmintonversion.entity.UserItem;
import com.myweather.app.badmintonversion.view.adapter.ItemRateAdapter;
import com.myweather.app.badmintonversion.view.fragment.main.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zyt on 2017/11/8.
 */

/**
 * 排名的Fragment
 * 1.0版本先不用懒加载模式
 */
public class UserDataRateFragment extends Fragment {
    public  static final String SECTIONNUMBER = "SECTIONNUMBER";
    private View view;
    private RecyclerView mRecyclerView;
    private ItemRateAdapter itemRateAdapter;
    private List<UserItem> userItems;
    private boolean flag;
    public static UserDataRateFragment getInstance(int sectionNumber){
        UserDataRateFragment fragment = new UserDataRateFragment();
        Bundle bunble= new Bundle();
        bunble.putInt(SECTIONNUMBER,sectionNumber);
        fragment.setArguments(bunble);
        return fragment;

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = initViews(inflater,container,savedInstanceState);
        initData();
        return view;




    }


    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_userdatarate,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_rate);
        flag = true;
        return view;
    }


    protected void initData() {

        userItems = new ArrayList<UserItem>();
        userItems.add(new UserItem("张三", "故事的小黄花，从出生那年就飘着", "辽宁省大连市", "100"));
        userItems.add(new UserItem("王琦山", "妖兽扰乱人间秩序", "辽宁省大连市", "100"));
        userItems.add(new UserItem("输赢", "传话不可有劲落下", "辽宁省大连市", "100"));
        userItems.add(new UserItem("六三炮", "一个人坐在空挡跑向里买哦", "辽宁省大连市", "100"));
        userItems.add(new UserItem("张三", "故事的小黄花，从出生那年就飘着", "辽宁省大连市", "100"));
        userItems.add(new UserItem("王琦山", "妖兽扰乱人间秩序", "辽宁省大连市", "100"));
        userItems.add(new UserItem("输赢", "传话不可有劲落下", "辽宁省大连市", "100"));
        userItems.add(new UserItem("六三炮", "一个人坐在空挡跑向里买哦", "辽宁省大连市", "100"));
        userItems.add(new UserItem("张三", "故事的小黄花，从出生那年就飘着", "辽宁省大连市", "100"));
        userItems.add(new UserItem("王琦山", "妖兽扰乱人间秩序", "辽宁省大连市", "100"));
        userItems.add(new UserItem("输赢", "传话不可有劲落下", "辽宁省大连市", "100"));
        userItems.add(new UserItem("六三炮", "一个人坐在空挡跑向里买哦", "辽宁省大连市", "100"));
        itemRateAdapter = new ItemRateAdapter(userItems, this.getArguments().getInt(SECTIONNUMBER));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(itemRateAdapter);


    }
   public void refresh(List<UserItem> userItems){

       if(flag){
           Log.d("refresh", "refresh: ");
       this.userItems = userItems;
       itemRateAdapter.notifyDataSetChanged();}

   }




}

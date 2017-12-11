package com.myweather.app.badmintonversion.view.fragment.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.UserItem;
import com.myweather.app.badmintonversion.model.Impl.BaseListener;
import com.myweather.app.badmintonversion.model.UserDataService;
import com.myweather.app.badmintonversion.view.adapter.ItemRateAdapter;
import com.myweather.app.badmintonversion.view.adapter.UserDataRateAdapter;
import com.myweather.app.badmintonversion.view.fragment.second.UserDataRateFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zyt on 2017/9/7.
 */

public class MatchFragment extends Fragment {
    @BindView(R.id.countText1)
    TextView countText1;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.calText2)
    TextView calText2;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.timeText3)
    TextView timeText3;
    @BindView(R.id.view3)
    View view3;

    Unbinder unbinder;
    @BindView(R.id.select_week)
    TextView selectWeek;
    @BindView(R.id.select_month)
    TextView selectMonth;
    @BindView(R.id.select_all)
    TextView selectAll;
    @BindView(R.id.recycler_match)
    RecyclerView recyclerMatch;

    private List<UserItem> userItems;
    private List<Fragment> fragments;
    private UserDataRateAdapter userDataRateAdapter;
    private boolean weekSelected=true;
    private boolean monthSelected;
    private boolean allSelected;
    private UserDataService userDataService;
    private ItemRateAdapter itemRateAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private boolean countFlag=true;
    private boolean calFlag;
    private boolean timeFlag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        weekSelected = true;
        monthSelected = false;
        allSelected = false;
        userDataService = new UserDataService();

        View view = inflater.inflate(R.layout.fragment_match, container, false);
        unbinder = ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(getActivity());
        userItems = new ArrayList<>();
        initData();
        itemRateAdapter = new ItemRateAdapter(userItems,1);
        recyclerMatch.setLayoutManager(layoutManager);
        recyclerMatch.setAdapter(itemRateAdapter);


        selectWeek.setBackground(getResources().getDrawable(R.drawable.button_left_background_pressed));

        fragments = new ArrayList<>();

        fragments.add(UserDataRateFragment.getInstance(1));
        fragments.add(UserDataRateFragment.getInstance(2));
        fragments.add(UserDataRateFragment.getInstance(3));
        userDataRateAdapter = new UserDataRateAdapter(getFragmentManager(), fragments);


        //viewpager.setAdapter(userDataRateAdapter);
        /*final UserDataRateFragment userDataRateFragment0 = (UserDataRateFragment) fragments.get(0);
       final UserDataRateFragment userDataRateFragment1 = (UserDataRateFragment) fragments.get(1);
        final UserDataRateFragment userDataRateFragment2 = (UserDataRateFragment) fragments.get(2);*/
        /*viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {


                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                        if(weekSelected==true){
                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("");
                            progressDialog.setMessage("");
                            progressDialog.setCancelable(false);
                            progressDialog.show();


                            userItems = userDataService.getUserItemList("thisWeekData4Count");

                            userDataRateFragment0.refresh(userItems);
                            progressDialog.dismiss();
                        };
                        if(monthSelected==true){
                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("");
                            progressDialog.setMessage("");
                            progressDialog.setCancelable(false);
                            progressDialog.show();

                            userItems = userDataService.getUserItemList("thisMonthData4Count");

                            userDataRateFragment0.refresh(userItems);
                            progressDialog.dismiss();
                        };
                        if(allSelected==true){
                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("");
                            progressDialog.setMessage("");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            userItems = userDataService.getUserItemList("allData4Count");

                            userDataRateFragment0.refresh(userItems);
                            progressDialog.dismiss();
                        };
                    }
                    break;
                    case 1: {

                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.GONE);
                        if(weekSelected==true){
                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("");
                            progressDialog.setMessage("");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            userItems = userDataService.getUserItemList("thisWeekData4Cal");

                            userDataRateFragment1.refresh(userItems);
                            progressDialog.dismiss();
                        };
                        if(monthSelected==true){
                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("");
                            progressDialog.setMessage("");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            userItems = userDataService.getUserItemList("thisMonthData4Cal");

                            userDataRateFragment1.refresh(userItems);
                            progressDialog.dismiss();
                        };
                        if(allSelected==true){
                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("");
                            progressDialog.setMessage("");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            userItems = userDataService.getUserItemList("allData4Cal");

                            userDataRateFragment1.refresh(userItems);
                            progressDialog.dismiss();
                        };
                    }
                    break;
                    case 2: {

                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.VISIBLE);
                        if(weekSelected==true){
                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("");
                            progressDialog.setMessage("");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            userItems = userDataService.getUserItemList("thisWeekData4Time");

                            userDataRateFragment2.refresh(userItems);
                            progressDialog.dismiss();
                        };
                        if(monthSelected==true){
                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("");
                            progressDialog.setMessage("");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            userItems = userDataService.getUserItemList("thisMonthData4Time");

                            userDataRateFragment2.refresh(userItems);
                            progressDialog.dismiss();
                        };
                        if(allSelected==true){
                            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setTitle("");
                            progressDialog.setMessage("");
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                            userItems = userDataService.getUserItemList("allData4Time");

                            userDataRateFragment2.refresh(userItems);
                            progressDialog.dismiss();
                        };
                    }
                    break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.countText1, R.id.calText2, R.id.timeText3, R.id.select_month, R.id.select_week, R.id.select_all})
    public void onViewClicked(View view) {


        switch (view.getId()) {
            case R.id.countText1: {
                countFlag = true;
                calFlag = false;
                timeFlag = false;
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                    
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("");
                    progressDialog.setMessage("请稍后....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                if(weekSelected){
                   userDataService.getUserItemList("thisWeekData4Count", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,1);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });}
                if(monthSelected){
                    userDataService.getUserItemList("thisMonthData4Count", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,1);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }
                if(allSelected){
                    userDataService.getUserItemList("allData4Count", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,1);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }



                //viewpager.setCurrentItem(0);
            }
            break;

            case R.id.calText2: {
                countFlag = false;
                calFlag = true;
                timeFlag = false;
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.GONE);

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("");
                progressDialog.setMessage("请稍后....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if(weekSelected){
                    userDataService.getUserItemList("thisWeekData4Cal", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,2);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });}
                if(monthSelected){
                    userDataService.getUserItemList("thisMonthData4Cal", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,2);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }
                if(allSelected){
                    userDataService.getUserItemList("allData4Cal", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,2);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }

                // viewpager.setCurrentItem(1);
            }
            break;

            case R.id.timeText3: {
                countFlag = false;
                calFlag = false;
                timeFlag = true;
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.VISIBLE);

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("");
                progressDialog.setMessage("请稍后....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if(weekSelected){
                    userDataService.getUserItemList("thisWeekData4Time", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,3);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });}
                if(monthSelected){
                    userDataService.getUserItemList("thisMonthData4Time", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,3);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }
                if(allSelected){
                    userDataService.getUserItemList("allData4Time", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,3);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }

                //viewpager.setCurrentItem(2);
            }
            break;
            case R.id.select_week: {
                weekSelected = true;
                monthSelected = false;
                allSelected = false;

                selectWeek.setBackground(getResources().getDrawable(R.drawable.button_left_background_pressed));
                selectMonth.setBackground(getResources().getDrawable(R.drawable.button_center_background));
                selectAll.setBackground(getResources().getDrawable(R.drawable.button_right_background));
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("");
                progressDialog.setMessage("请稍后....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if(countFlag){

                    userDataService.getUserItemList("thisWeekData4Count", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,1);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });}
                if(calFlag){
                    userDataService.getUserItemList("thisWeekData4Cal", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,2);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }
                if(timeFlag){
                    userDataService.getUserItemList("thisWeekData4Time", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,3);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }

            }
            break;
            case R.id.select_month: {
                weekSelected = false;
                monthSelected = true;
                allSelected = false;
                selectWeek.setBackground(getResources().getDrawable(R.drawable.button_left_background));
                selectMonth.setBackground(getResources().getDrawable(R.drawable.button_center_background_pressed));
                selectAll.setBackground(getResources().getDrawable(R.drawable.button_right_background));
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("");
                progressDialog.setMessage("请稍后....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if(countFlag){
                    userDataService.getUserItemList("thisMonthData4Count", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,1);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });}
                if(calFlag){
                    userDataService.getUserItemList("thisMonthData4Cal", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,2);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }
                if(timeFlag){
                    userDataService.getUserItemList("thisMonthData4Time", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,3);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }
            }
            break;
            case R.id.select_all: {
                weekSelected = false;
                monthSelected = false;
                allSelected = true;
                selectWeek.setBackground(getResources().getDrawable(R.drawable.button_left_background));
                selectMonth.setBackground(getResources().getDrawable(R.drawable.button_center_background));
                selectAll.setBackground(getResources().getDrawable(R.drawable.button_right_background_pressed));

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("");
                progressDialog.setMessage("请稍后....");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if(countFlag){
                    userDataService.getUserItemList("allData4Count", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,1);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });}
                if(calFlag){
                    userDataService.getUserItemList("allData4Cal", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,2);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }
                if(timeFlag){
                    userDataService.getUserItemList("allData4Time", new BaseListener() {
                        @Override
                        public void getSuccess(Object o) {

                            List<UserItem> newUserItems=(List<UserItem>) o;

                            itemRateAdapter = new ItemRateAdapter(newUserItems,3);
                            recyclerMatch.setAdapter(itemRateAdapter);
                            //itemRateAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void getFailure(Exception e) {

                        }
                    });
                }
            }
            break;


        }
    }

    protected void initData() {


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



    }
}

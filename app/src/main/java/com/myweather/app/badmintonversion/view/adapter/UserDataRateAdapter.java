package com.myweather.app.badmintonversion.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zyt on 2017/11/8.
 */

public class UserDataRateAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public UserDataRateAdapter(FragmentManager fm, List<Fragment> fragments) {


        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}


package com.myweather.app.badmintonversion.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.myweather.app.badmintonversion.view.fragment.second.InstallFragment;

/**
 * Created by zyt on 2017/9/8.
 */

public class InstallViewPaperAdapter extends FragmentPagerAdapter {
    public InstallViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return InstallFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}

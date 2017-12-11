package com.myweather.app.badmintonversion.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.myweather.app.badmintonversion.view.fragment.second.SportsAnalysisFragment;

import java.util.List;

/**
 * Created by zyt on 2017/8/25.
 */


public class SportsAnalysisViewPaperAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public SportsAnalysisViewPaperAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        //return SportsAnalysisFragment.newInstance(position+1);
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
            case 3:
                return "SECTION 4";
            case 4:
                return "SECTION 5";
        }
        return null;
    }
}

package com.myweather.app.badmintonversion.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.myweather.app.badmintonversion.view.fragment.second.QuickStartFragment;


/**
 * Created by zyt on 2017/8/17.
 */

public class QuickStartViewPaperAdapter extends FragmentPagerAdapter {
    public QuickStartViewPaperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return QuickStartFragment.newInstance(position+1);
    }

    @Override
    public int getCount() {
        return 5;
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

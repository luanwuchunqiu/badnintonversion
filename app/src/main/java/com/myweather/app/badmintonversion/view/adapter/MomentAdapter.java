package com.myweather.app.badmintonversion.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.myweather.app.badmintonversion.entity.Moment;

import java.util.ArrayList;

/**
 * Created by zyt on 2017/10/18.
 */

public class MomentAdapter extends BaseAdapter {
    private ArrayList<Moment> moments;
    public MomentAdapter(ArrayList<Moment> moments){
        this.moments = moments;
    }
    @Override
    public int getCount() {

        return moments.size();
    }

    @Override
    public Object getItem(int position) {
        return moments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

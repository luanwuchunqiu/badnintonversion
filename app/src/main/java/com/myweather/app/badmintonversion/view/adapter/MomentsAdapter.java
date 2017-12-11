package com.myweather.app.badmintonversion.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.UserMoment;
import com.myweather.app.badmintonversion.view.nine.CustomImageView;
import com.myweather.app.badmintonversion.view.nine.Image;
import com.myweather.app.badmintonversion.view.nine.NineGridlayout;
import com.myweather.app.badmintonversion.view.nine.ScreenTools;

import java.util.List;

/**
 * Created by zyt on 2017/10/11.
 */

public class MomentsAdapter extends BaseAdapter{
    private Context context;
    private List<UserMoment> userMoments;

    public MomentsAdapter(Context context, List<UserMoment> userMoments) {
        this.context = context;
        this.userMoments = userMoments;
    }

    @Override
    public int getCount() {
        return userMoments.size();
    }

    @Override
    public Object getItem(int position) {
        return userMoments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        UserMoment userMoment = (UserMoment) getItem(position);
        List<Image> itemList = userMoment.getMomentImages();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_usermoments, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivMore = (NineGridlayout) convertView.findViewById(R.id.iv_ngrid_layout);
            viewHolder.ivOne = (CustomImageView) convertView.findViewById(R.id.iv_oneimage);
            viewHolder.momentsTextView =(TextView) convertView.findViewById(R.id.text_view_moment_text);
            viewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.text_view_userName);
            viewHolder.userIdImageView = (ImageView) convertView.findViewById(R.id.image_view_user_head);

            convertView.setTag(viewHolder);
        } else {
            viewHolder =  (ViewHolder) convertView.getTag();
        }
        if (itemList.isEmpty() || itemList.isEmpty()) {
            viewHolder.ivMore.setVisibility(View.GONE);
            viewHolder.ivOne.setVisibility(View.GONE);
        } else if (itemList.size() == 1) {
            viewHolder.ivMore.setVisibility(View.GONE);
            viewHolder.ivOne.setVisibility(View.VISIBLE);

            handlerOneImage(viewHolder, itemList.get(0));
        } else {
            viewHolder.ivMore.setVisibility(View.VISIBLE);
            viewHolder.ivOne.setVisibility(View.GONE);

            viewHolder.ivMore.setImagesData(itemList);
        }

        viewHolder.userIdImageView.setBackgroundResource(userMoment.getHeadImageId());
        viewHolder.userNameTextView.setText(userMoment.getUserName());
        viewHolder.momentsTextView.setText(userMoment.getMomentText());

        return convertView;
    }


    private void handlerOneImage(ViewHolder viewHolder, Image image) {
        int totalWidth;
        int imageWidth;
        int imageHeight;
        ScreenTools screentools = ScreenTools.instance(context);
        totalWidth = screentools.getScreenWidth() - screentools.dip2px(80);
        imageWidth = screentools.dip2px(image.getWidth());
        imageHeight = screentools.dip2px(image.getHeight());
        if (image.getWidth() <= image.getHeight()) {
            if (imageHeight > totalWidth) {
                imageHeight = totalWidth;
                imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            }
        } else {
            if (imageWidth > totalWidth) {
                imageWidth = totalWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
        }
        ViewGroup.LayoutParams layoutparams = viewHolder.ivOne.getLayoutParams();
        layoutparams.height = imageHeight;
        layoutparams.width = imageWidth;
        viewHolder.ivOne.setLayoutParams(layoutparams);
        viewHolder.ivOne.setClickable(true);
        viewHolder.ivOne.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.ivOne.setImageUrl(image.getUrl());

    }

    class ViewHolder {
        public TextView userNameTextView;
        public TextView momentsTextView;
        public ImageView userIdImageView;
        public NineGridlayout ivMore;
        public CustomImageView ivOne;
    }
}

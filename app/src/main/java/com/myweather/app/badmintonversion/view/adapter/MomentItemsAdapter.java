package com.myweather.app.badmintonversion.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.MomentItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zyt on 2017/11/27.
 */

public class MomentItemsAdapter extends BaseAdapter {
    private Context mContext;
    private List<MomentItem> momentItemList;

    public MomentItemsAdapter() {
    }

    public MomentItemsAdapter(Context mContext, List<MomentItem> momentItemList) {
        this.mContext = mContext;
        this.momentItemList = momentItemList;
    }

    @Override
    public int getCount() {
        return momentItemList.size();
    }

    @Override
    public MomentItem getItem(int position) {
        return momentItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MomentItem momentItem = momentItemList.get(position);
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_nine_grid_view, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Glide.with(mContext).load(momentItem.getUserPhotoUrl()).into(holder.avatar);
        holder.tvUsername.setText(momentItem.getUsername());
        holder.tvContent.setText(momentItem.getContent());
        /**
         *
         */
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = momentItem.getPhotosUrls();
        if (imageDetails != null) {
            for (String imageDetail : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail);
                info.setBigImageUrl(imageDetail);
                imageInfo.add(info);
            }
        }
        holder.nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.avatar)
        CircleImageView avatar;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.nineGrid)
        NineGridView nineGrid;
        @BindView(R.id.tv_createTime)
        TextView tvCreateTime;
        @BindView(R.id.delete)
        TextView delete;
        @BindView(R.id.more)
        ImageView more;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

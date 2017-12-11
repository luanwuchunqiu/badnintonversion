package com.myweather.app.badmintonversion.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.VideoItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by zyt on 2017/12/6.
 */

public class VideoPlayerAdapter extends RecyclerView.Adapter<VideoPlayerAdapter.ViewHolder> {
    private List<VideoItem> mVideoItemList;
    private Context mContext;

    public VideoPlayerAdapter(List<VideoItem> mVideoItemList, Context mContext) {
        this.mVideoItemList = mVideoItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder ;
    }

    /**
     * 可以添加为一个图片然后通过图片打开内容
     * 而不是直接进行视频的播放
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VideoItem videoItem = mVideoItemList.get(position);
        holder.itemPlayer.setUp(videoItem.getVideoUrl(), JZVideoPlayer.SCREEN_WINDOW_LIST,videoItem.getVideoTitle());

        Glide.with(mContext)
                .load(videoItem.getVideoCoverUrl())
                .into(holder.itemPlayer.thumbImageView);
        holder.itemPlayer.positionInList = position;
    }

    @Override
    public int getItemCount() {
        return mVideoItemList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_player)
        JZVideoPlayerStandard itemPlayer;



        public ViewHolder(View itemView) {
            super(itemView);
                ButterKnife.bind(this, itemView);

        }
    }
}

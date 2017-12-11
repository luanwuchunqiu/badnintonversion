package com.myweather.app.badmintonversion.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.myweather.app.badmintonversion.R;

import java.util.List;

/**
 * Created by zyt on 2017/10/17.
 */

public class MomentPictureAdapter extends RecyclerView.Adapter<MomentPictureAdapter.ViewHolder>{
    private Context mContext;
    private List<String> urls;

    public MomentPictureAdapter(List<String> urls) {
        this.urls = urls;
    }

    //添加点击事件
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    private OnItemClickLitener mOnItemClickLitener;

    public OnItemClickLitener getmOnItemClickLitener() {
        return mOnItemClickLitener;
    }

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext= parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moment_picture,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String url = urls.get(position);
        Glide.with(mContext).load(url).into(holder.imageView);
        







        if(mOnItemClickLitener!=null){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos =holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.imageView,pos);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_moment_picture);


        }
    }
}

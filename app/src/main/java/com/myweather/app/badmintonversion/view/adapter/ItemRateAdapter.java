package com.myweather.app.badmintonversion.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myweather.app.badmintonversion.R;
import com.myweather.app.badmintonversion.entity.UserItem;


import java.util.List;

/**
 * Created by zyt on 2017/11/7.
 */

public class ItemRateAdapter extends RecyclerView.Adapter<ItemRateAdapter.ViewHolder>{

    private List<UserItem> userItems;
    private int dataType;

    public int getDataType() {
        return dataType;
    }

    public ItemRateAdapter(List<UserItem> userItems, int dataType) {
        this.userItems = userItems;
        this.dataType = dataType;
    }

    @Override
    public ItemRateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemRateAdapter.ViewHolder holder, int position) {

        holder.userDataTextView.setText(userItems.get(position).getUserData());
        holder.statusTextView.setText(userItems.get(position).getStatus());
        holder.addressTextView.setText(userItems.get(position).getAddress());
        holder.userNameTextView.setText(userItems.get(position).getUserName());
        switch (dataType){
            case 1:{holder.unitTextView.setText("次");}break;
            case 2:{holder.unitTextView.setText("Cal");}break;
            case 3:{holder.unitTextView.setText("h");}break;
        }
        holder.rate.setText(position+1+"");

    }

    @Override
    public int getItemCount() {
        return userItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView;
         TextView addressTextView;
         TextView statusTextView;
         TextView userDataTextView;
         TextView unitTextView;
        TextView rate;
        public ViewHolder(View itemView) {

            super(itemView);
            userNameTextView = (TextView) itemView.findViewById(R.id.userName);
            addressTextView = (TextView) itemView.findViewById(R.id.userAddress);
            statusTextView = (TextView) itemView.findViewById(R.id.userStatus);
            userDataTextView =  (TextView) itemView.findViewById(R.id.userData);
            unitTextView = (TextView) itemView.findViewById(R.id.unit);
            rate = (TextView) itemView.findViewById(R.id.rate);
        }
    }
}

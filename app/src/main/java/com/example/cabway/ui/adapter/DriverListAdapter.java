package com.example.cabway.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabway.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.DriverViewHolder> {

    private Context mContext;
    private ArrayList<String> driverList;
    private int clickedPosition;

    //TODO:
    /*public DriverListAdapter(Context context, List<DriverModel> driverDataList) {

        this.mContext = context;
        this.driverList = new ArrayList<DriverModel>();
        this.driverList.addAll(driverDataList);
    }*/



    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_item, parent, false);
        return new DriverViewHolder(mItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        //TODO:
       /* DriverModel driver =driverList.get(position);

        ImageUtils.setImageFromUrl(this,driver.getImageUrl(),holder.ivProfile);
        holder.tvName.setText(driver.getName());
        holder.tvMobileNo.setText(driver.getMobNo);*/
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    static class DriverViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView ivProfile;
        private TextView tvName, tvMobileNo;

        DriverViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ivProfile = itemLayoutView.findViewById(R.id.iv_profile);
            tvName = itemLayoutView.findViewById(R.id.tv_driver_name);
            tvMobileNo = itemLayoutView.findViewById(R.id.tv_mobile_no);
        }
    }

}




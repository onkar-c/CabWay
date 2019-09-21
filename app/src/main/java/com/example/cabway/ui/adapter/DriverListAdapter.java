package com.example.cabway.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabway.R;
import com.example.cabway.Utils.ImageUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.ui.activities.DriverDetailsActivity;
import com.example.cabway.ui.activities.RideDetailPage;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.DriverViewHolder> {

    private Context mContext;
    private List<UserModel> driverList;


    public DriverListAdapter(Context context, List<UserModel> driverDataList) {
        this.mContext = context;
        this.driverList = driverDataList;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_item, parent, false);
        return new DriverViewHolder(mItemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        UserModel driver = driverList.get(position);
        ImageUtils.setImageFromUrl(mContext, driver.profileImage, holder.ivProfile,R.drawable.ic_profile_icon);
        holder.tvMobileNo.setText(driver.mobileNo);
        holder.tvName.setText(String.format("%s %s", driver.firstName, driver.lastName));
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    public void resetData(List<UserModel> driverList) {
        this.driverList = driverList;
        notifyDataSetChanged();
    }

    class DriverViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_profile)
        CircleImageView ivProfile;
        @BindView(R.id.tv_driver_name)
        TextView tvName;
        @BindView(R.id.tv_mobile_no)
        TextView tvMobileNo;

        DriverViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.driver_layout)
        void onMenuItemClick(View view) {
            Intent nextActivity = new Intent(mContext, DriverDetailsActivity.class);
            nextActivity.putExtra(IntentConstants.RIDE, ((RideDetailPage) mContext).ride);
            nextActivity.putExtra(IntentConstants.DRIVER_DETAILS, driverList.get(getAdapterPosition()));
            ((RideDetailPage) mContext).startActivityForResult(nextActivity, AppConstants.REFRESH);
        }
    }

}




package com.example.cabway.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabway.R;
import com.example.cabway.Utils.DatePickerUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.ui.activities.HistoryListActivity;
import com.example.cabway.ui.activities.RideDetailPage;
import com.example.core.responseModel.RideResponseModel;
import com.example.database.Utills.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RidesListAdapter extends RecyclerView.Adapter<RidesListAdapter.RidesListViewHolder> {

    private Context context;
    private List<RideResponseModel> ridesList;
    private boolean isAgency, isFromHistory;

    public RidesListAdapter(Context context, List<RideResponseModel> ridesList, boolean isAgency, boolean isFromHistory) {
        this.context = context;
        this.ridesList = ridesList;
        this.isAgency = isAgency;
        this.isFromHistory = isFromHistory;
    }

    @NonNull
    @Override
    public RidesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_trip_list_item, parent, false);
        return new RidesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RidesListViewHolder holder, int position) {
        RideResponseModel ride = ridesList.get(position);
        if (!isAgency) {
            holder.agencyName.setVisibility(View.VISIBLE);
            holder.agencyName.setText(ride.getAgency().getAgencyName());
        } else
            holder.agencyName.setVisibility(View.GONE);
        holder.rideCost.setText(String.format("%s ", ride.getCost().toString()));
        holder.rideDate.setText(DatePickerUtils.convertDate(ride.getPickupTime()));
        holder.rideTime.setText(DatePickerUtils.convertDate(ride.getDropTime()));
        holder.fromCity.setText(ride.getFromCity().getName());
        holder.toCity.setText(ride.getToCity().getName());
        holder.km.setText(String.format("%s ", ride.getDistance().toString()));
        holder.status.setVisibility(isFromHistory && isAgency ? View.VISIBLE : View.GONE);
        if (isFromHistory && isAgency)
            holder.status.setImageResource(ride.getStatus().equals(AppConstants.EXPIRED) ? R.drawable.otp_cross : R.drawable.ic_check_circle_yellow);

    }

    @Override
    public int getItemCount() {
        return (ridesList != null) ? ridesList.size() : 0;
    }

    public void setData(List<RideResponseModel> ridesList) {
        this.ridesList = ridesList;
        notifyDataSetChanged();
    }

    class RidesListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.agencyName)
        TextView agencyName;
        @BindView(R.id.rideCost)
        TextView rideCost;
        @BindView(R.id.rideDate)
        TextView rideDate;
        @BindView(R.id.rideTime)
        TextView rideTime;
        @BindView(R.id.fromCity)
        TextView fromCity;
        @BindView(R.id.toCity)
        TextView toCity;
        @BindView(R.id.km)
        TextView km;
        @BindView(R.id.status)
        ImageView status;

        RidesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.rideCard)
        void onMenuItemClick(View view) {
            Intent rideDetailActivity = new Intent(context, RideDetailPage.class);
            rideDetailActivity.putExtra(IntentConstants.RIDE, ridesList.get(this.getAdapterPosition()));
            if (context instanceof HistoryListActivity)
                rideDetailActivity.putExtra(IntentConstants.IS_FROM_HISTORY, true);
            context.startActivity(rideDetailActivity);

        }
    }
}



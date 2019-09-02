package com.example.cabway.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabway.R;
import com.example.cabway.ui.activities.DashBoardActivity;
import com.example.cabway.ui.adapter.RidesListAdapter;
import com.example.core.responseModel.RideResponseModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestedRidesFragment extends Fragment {

    public RidesListAdapter ridesListAdapter;
    @BindView(R.id.availableRidesList)
    RecyclerView availableRidesList;
    @BindView(R.id.no_data_available)
    RelativeLayout noDataAvailable;
    private List<RideResponseModel> rides;
    private DashBoardActivity activityContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_requested_rides, container, false);
        ButterKnife.bind(this, root);
        activityContext = (DashBoardActivity) getActivity();
        rides = Objects.requireNonNull(activityContext).requestedRides;
        setRidesList();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setRidesList() {
        if (rides == null || rides.size() == 0) {
            noDataAvailable.setVisibility(View.VISIBLE);
        } else {
            noDataAvailable.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(activityContext);
            availableRidesList.setLayoutManager(layoutManager);
            ridesListAdapter = new RidesListAdapter(activityContext, rides);
            availableRidesList.setAdapter(ridesListAdapter);
        }
    }
}
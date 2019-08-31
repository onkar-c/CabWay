package com.example.cabway.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.cabway.R;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;
import com.example.cabway.ui.activities.DashBoardActivity;
import com.example.cabway.ui.activities.DriverRideDetailPage;
import com.example.cabway.ui.adapter.RidesListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestedRidesFragment extends Fragment {

    @BindView(R.id.availableRidesList)
    RecyclerView availableRidesList;
    RidesListAdapter ridesListAdapter;
    @BindView(R.id.no_data_available)
    RelativeLayout noDataAvailable;
    List<String> data;
    private DashBoardActivity activityContext;
    RecyclerViewItemClickListener ridesViewItemClickListener = (v, position) -> startActivity(new Intent(activityContext, DriverRideDetailPage.class));


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_requested_rides, container, false);
        ButterKnife.bind(this, root);
        activityContext = (DashBoardActivity) getActivity();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        data = new ArrayList<>();
        setRidesList();
    }

    private void setRidesList() {
        if (data == null || data.size() == 0) {
            noDataAvailable.setVisibility(View.VISIBLE);
        } else {
            noDataAvailable.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(activityContext);
            availableRidesList.setLayoutManager(layoutManager);
            ridesListAdapter = new RidesListAdapter(activityContext, data, ridesViewItemClickListener);
            availableRidesList.setAdapter(ridesListAdapter);
        }
    }
}
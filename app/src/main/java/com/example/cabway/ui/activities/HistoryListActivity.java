package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabway.R;
import com.example.cabway.ui.adapter.RidesListAdapter;
import com.example.cabway.viewModels.RidesViewModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.responseModel.RideResponseModel;
import com.example.database.Utills.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryListActivity extends BaseActivity {
    @BindView(R.id.ridesHistoryList)
    RecyclerView ridesHistoryList;
    @BindView(R.id.no_data_available)
    RelativeLayout noDataAvailable;

    RidesListAdapter ridesListAdapter;
    RidesViewModel ridesViewModel;
    List<RideResponseModel> rides = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        ButterKnife.bind(this);
        ridesViewModel = ViewModelProviders.of(this).get(RidesViewModel.class);
        ridesViewModel.init();
        setUpActionBar();
        setRidesHistoryObserver();
        setRidesList();
    }

    private void setRidesList() {
        if(rides != null && rides.size() > 0) {
            ridesHistoryList.setVisibility(View.VISIBLE);
            noDataAvailable.setVisibility(View.GONE);
            if (ridesListAdapter == null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                ridesHistoryList.setLayoutManager(layoutManager);
                ridesListAdapter = new RidesListAdapter(this, rides, appPreferences.getUserDetails().role.equals(AppConstants.AGENCY), true);
                ridesHistoryList.setAdapter(ridesListAdapter);
            } else
                ridesListAdapter.setData(rides);

        } else {
            ridesHistoryList.setVisibility(View.GONE);
            noDataAvailable.setVisibility(View.VISIBLE);
        }
    }

    private void setRidesHistoryObserver(){
        ridesViewModel.getRidesHistoryResponseMld().observe(this, (JsonResponse getRidesHistoryResponse) -> {
            removeProgressDialog();
            if (isSuccessResponse(getRidesHistoryResponse)) {
                rides = getRidesHistoryResponse.getRideList();
                setRidesList();
            } else {
                Toast.makeText(HistoryListActivity.this, getRidesHistoryResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRidesHistory(){
        if (checkNetworkAvailableWithoutError()) {
            showProgressDialog(AppConstants.PLEASE_WAIT, false);
            ridesViewModel.getRidesRepository().getRidesHistory(ridesViewModel.getRidesHistoryResponseMld());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRidesHistory();
    }
}
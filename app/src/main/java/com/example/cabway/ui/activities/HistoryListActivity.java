package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cabway.R;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;
import com.example.cabway.ui.adapter.RidesListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryListActivity extends BaseActivity {
    @BindView(R.id.ridesHistoryList)
    RecyclerView ridesHistoryList;

    RidesListAdapter ridesListAdapter;
    RecyclerViewItemClickListener ridesViewItemClickListener = (v, position) -> {
        Intent nextActivity = new Intent(HistoryListActivity.this, DriverRideDetailPage.class);
        nextActivity.putExtra(IntentConstants.IS_FROM_HISTORY, true);
        HistoryListActivity.this.startActivity(nextActivity);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        ButterKnife.bind(this);
        setUpActionBar();
        setRidesList();
    }

    private void setRidesList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ridesHistoryList.setLayoutManager(layoutManager);
        ridesListAdapter = new RidesListAdapter(this, new ArrayList<>(), ridesViewItemClickListener);
        ridesHistoryList.setAdapter(ridesListAdapter);
    }
}
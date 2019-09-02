package com.example.cabway.ui.activities;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cabway.R;
import com.example.cabway.ui.adapter.RidesListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryListActivity extends BaseActivity {
    @BindView(R.id.ridesHistoryList)
    RecyclerView ridesHistoryList;

    RidesListAdapter ridesListAdapter;

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
        ridesListAdapter = new RidesListAdapter(this, new ArrayList<>());
        ridesHistoryList.setAdapter(ridesListAdapter);
    }
}
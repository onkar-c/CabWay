package com.example.cabway.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnGoingRidesFragment extends Fragment {

    @BindView(R.id.availableRidesList)
    RecyclerView availableRidesList;
    @BindView(R.id.no_data_available)
    RelativeLayout noDataAvailable;
    private DashBoardActivity activityContext;
    private List<String> data;
    private RecyclerViewItemClickListener ridesViewItemClickListener = (v, position) -> startActivity(new Intent(activityContext, DriverRideDetailPage.class));

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i("fragment", "onCreateView");
        View root = inflater.inflate(R.layout.fragment_ongoing_rides, container, false);
        ButterKnife.bind(this, root);
        activityContext = (DashBoardActivity) getActivity();
        return root;
    }

    private void setRidesList() {
        if(data == null || data.size() == 0){
            noDataAvailable.setVisibility(View.VISIBLE);
        } else {
            noDataAvailable.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(activityContext);
            availableRidesList.setLayoutManager(layoutManager);
            RidesListAdapter ridesListAdapter = new RidesListAdapter(activityContext, data, ridesViewItemClickListener);
            availableRidesList.setAdapter(ridesListAdapter);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("fragment", "onAttach");
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("fragment", "onCreate");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("fragment", "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("fragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        data = new ArrayList<>();
        data.add(Objects.requireNonNull(activityContext).getMenu_items().get(0));
        setRidesList();
        Log.i("fragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("fragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("fragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("fragment", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("fragment", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("fragment", "onDetach");
    }
}
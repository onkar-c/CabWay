package com.example.cabway.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cabway.R;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AvailableRidesListAdapter extends RecyclerView.Adapter<AvailableRidesListAdapter.AvailableRidesListViewHolder> {

    private Context context;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;


    public AvailableRidesListAdapter(Context context, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.context = context;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public AvailableRidesListAdapter.AvailableRidesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_trip_list_item, parent, false);
        return new AvailableRidesListAdapter.AvailableRidesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableRidesListAdapter.AvailableRidesListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class AvailableRidesListViewHolder extends RecyclerView.ViewHolder {


        AvailableRidesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.rideCard)
        void onMenuItemClick(View view) {
            recyclerViewItemClickListener.onItemClick(view, this.getAdapterPosition());
        }
    }
}



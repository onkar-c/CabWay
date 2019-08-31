package com.example.cabway.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cabway.R;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RidesListAdapter extends RecyclerView.Adapter<RidesListAdapter.RidesListViewHolder> {

    private Context context;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    private List<String> ridesList;

    public RidesListAdapter(Context context,List<String> ridesList, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.context = context;
        this.ridesList = ridesList;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public RidesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_trip_list_item, parent, false);
        return new RidesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RidesListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (ridesList != null) ? ridesList.size() : 0;
    }

    class RidesListViewHolder extends RecyclerView.ViewHolder {
        RidesListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.rideCard)
        void onMenuItemClick(View view) {
            recyclerViewItemClickListener.onItemClick(view, this.getAdapterPosition());
        }
    }
}



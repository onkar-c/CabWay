package com.example.cabway.ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cabway.R;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuAdapter.ProfileMenuViewHolder> {
    private Context context;
    private List<String> mMenuItems;
    private TypedArray mMenuItemsIcons;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public ProfileMenuAdapter(Context context, List<String> menuItems, TypedArray menuItemsIcon, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.context = context;
        this.mMenuItems = menuItems;
        this.mMenuItemsIcons = menuItemsIcon;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public ProfileMenuAdapter.ProfileMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_menu_item, parent, false);
        return new ProfileMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMenuAdapter.ProfileMenuViewHolder holder, int position) {
        holder.menuTitle.setText(mMenuItems.get(position));
        holder.itemImage.setImageResource(mMenuItemsIcons.getResourceId(position, 0));
    }

    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }

    class ProfileMenuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.menu_title)
        TextView menuTitle;
        @BindView(R.id.itemImage)
        ImageView itemImage;

        ProfileMenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.profileMenuItem)
        void onMenuItemClick(View view) {
            recyclerViewItemClickListener.onItemClick(view, this.getAdapterPosition());
        }
    }
}

package com.example.cabway.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cabway.R;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.DocumentListViewHolder> {

    private Context context;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    private List<String> documentList;


    public DocumentListAdapter(Context context, List<String> documentList, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.context = context;
        this.documentList = documentList;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public DocumentListAdapter.DocumentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.document_type_item, parent, false);
        return new DocumentListAdapter.DocumentListViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DocumentListAdapter.DocumentListViewHolder holder, int position) {
        String displayText = context.getString(R.string.step) + " " + (position + 1) + " : " + documentList.get(position);
        holder.documentType.setText(displayText);
        if(position%2 == 0) {
            holder.documentType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.otp_cross, 0);
            holder.documentType.setCompoundDrawablePadding(20);
        }
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    class DocumentListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.document_type)
        TextView documentType;

        DocumentListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.document_type)
        void onMenuItemClick(View view){
            recyclerViewItemClickListener.onItemClick(view,this.getAdapterPosition());
        }
    }
}

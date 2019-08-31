package com.example.cabway.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cabway.R;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;
import com.example.cabway.ui.activities.DocumentListActivity;
import com.example.core.CommonModels.DocumentModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.DocumentListViewHolder> {

    private Context context;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    private List<String> documentList;
    private List<DocumentModel> documentModelList;


    public DocumentListAdapter(Context context, List<String> documentList, List<DocumentModel> documentModelList, RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.context = context;
        this.documentList = documentList;
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
        this.documentModelList = documentModelList;
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
        if(documentModelList != null && DocumentListActivity.getDocumentFromList(documentList.get(position), documentModelList) != null)
            holder.documentType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_yellow, 0);
        else
            holder.documentType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.otp_cross, 0);
        holder.documentType.setCompoundDrawablePadding(20);
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    public void setDocumentModelList(List<DocumentModel> documentModelList){
        this.documentModelList = documentModelList;
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

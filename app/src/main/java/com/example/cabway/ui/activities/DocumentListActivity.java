package com.example.cabway.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.cabway.R;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;
import com.example.cabway.ui.adapter.DocumentListAdapter;
import com.example.database.Utills.AppConstants;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentListActivity extends BaseActivity {

    @BindView(R.id.documentList)
    RecyclerView DocumentList;

    DocumentListAdapter documentListAdapter;
    private List<String> documentList;
    RecyclerViewItemClickListener recyclerViewItemClickListener = new RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Intent intent = new Intent(DocumentListActivity.this, UploadDocumentActivity.class);
            intent.putExtra(IntentConstants.DOC_TYPE_EXTRA, documentList.get(position));
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_list);
        setUpActionBar();
        ButterKnife.bind(this);
        initData();
        setDocumentList();
    }

    private void setDocumentList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DocumentList.setLayoutManager(layoutManager);
        documentListAdapter = new DocumentListAdapter(this, documentList, recyclerViewItemClickListener);
        DocumentList.setAdapter(documentListAdapter);
    }

    private void initData() {
        documentList = Arrays.asList(getResources().getStringArray((appPreferences.getUserDetails().role.equals(AppConstants.DRIVER)) ? R.array.driver_document_list : R.array.agency_document_list));
    }
}

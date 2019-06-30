package com.example.cabway.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cabway.R;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;
import com.example.cabway.ui.adapter.DocumentListAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentListActivity extends BaseActivity {

    @BindView(R.id.documentList)
    RecyclerView DocumentList;

    DocumentListAdapter documentListAdapter;
    RecyclerViewItemClickListener recyclerViewItemClickListener = (v, position) -> {
//            String menuItem = documentList.get(position);
    };
    private List<String> documentList;

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
        documentList = Arrays.asList(getResources().getStringArray(R.array.document_list));
    }
}

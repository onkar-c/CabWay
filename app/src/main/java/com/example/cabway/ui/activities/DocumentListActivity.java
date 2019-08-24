package com.example.cabway.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.DialogUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;
import com.example.cabway.ui.adapter.DocumentListAdapter;
import com.example.cabway.viewModels.DocumentViewModel;
import com.example.core.CommonModels.DocumentModel;
import com.example.database.Utills.AppConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DocumentListActivity extends BaseActivity {

    @BindView(R.id.documentList)
    RecyclerView DocumentList;
    DocumentListAdapter documentListAdapter;
    @BindView(R.id.btn_logout)
    Button btn_logout;
    private DocumentViewModel documentViewModel;
    private List<String> documentList;
    private List<DocumentModel> documentModelList;
    RecyclerViewItemClickListener recyclerViewItemClickListener = new RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Intent intent = new Intent(DocumentListActivity.this, UploadDocumentActivity.class);
            intent.putExtra(IntentConstants.DOCUMENT_EXTRA, getDocumentFromList(documentList.get(position), documentModelList));
            intent.putExtra(IntentConstants.DOC_TYPE_EXTRA, documentList.get(position));
            startActivity(intent);
        }
    };

    public static DocumentModel getDocumentFromList(String docType, List<DocumentModel> documentModels) {
        if (!documentModels.isEmpty()) {
            for (DocumentModel document : documentModels) {
                if (document.getDocumentType().equals(docType))
                    return document;
            }
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_list);
        ButterKnife.bind(this);
        boolean isFromLogin = getIntent().getBooleanExtra(IntentConstants.IS_FROM_LOGIN, false);
        if (isFromLogin)
            btn_logout.setVisibility(View.VISIBLE);
        else {
            btn_logout.setVisibility(View.GONE);
            setUpActionBar();
        }


        documentViewModel = ViewModelProviders.of(this).get(DocumentViewModel.class);
        documentViewModel.init();
        setDocumentsObserver();
        initData();
        setDocumentList();
        showProgressDialog(AppConstants.PLEASE_WAIT, false);
        documentViewModel.getDocumentRepository().getDocuments(documentViewModel.getDocumentsResponseMld());
    }

    private void setDocumentsObserver() {
        documentViewModel.getDocumentsResponseMld().observe(this, documentsResponse -> {
            removeProgressDialog();
            if (Objects.requireNonNull(documentsResponse).getStatus().equals(AppConstants.SUCCESS)) {
                documentModelList = documentsResponse.getDocumentsList();
                documentListAdapter.setDocumentModelList(documentModelList);
                documentListAdapter.notifyDataSetChanged();
                Toast.makeText(DocumentListActivity.this, documentsResponse.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                documentModelList = Collections.emptyList();
                Toast.makeText(DocumentListActivity.this, documentsResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDocumentList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DocumentList.setLayoutManager(layoutManager);
        documentListAdapter = new DocumentListAdapter(this, documentList, documentModelList, recyclerViewItemClickListener);
        DocumentList.setAdapter(documentListAdapter);
    }

    private void initData() {
        documentList = Arrays.asList(getResources().getStringArray((appPreferences.getUserDetails().role.equals(AppConstants.DRIVER)) ? R.array.driver_document_list : R.array.agency_document_list));
    }

    @OnClick(R.id.btn_logout)
    public void performLogout() {
        DialogUtils.showMessageDialog(this, getString(R.string.logout_message), onLogoutListener);
    }
}

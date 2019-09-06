package com.example.cabway.ui.activities;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabway.R;
import com.example.cabway.Utils.DialogUtils;
import com.example.cabway.Utils.IntentConstants;
import com.example.cabway.ui.Interfaces.RecyclerViewItemClickListener;
import com.example.cabway.ui.adapter.DocumentListAdapter;
import com.example.cabway.viewModels.DocumentViewModel;
import com.example.core.CommonModels.DocumentModel;
import com.example.core.CommonModels.UserModel;
import com.example.database.Utills.AppConstants;

import java.util.Arrays;
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
    @BindView(R.id.btn_continue)
    Button btn_continue;
    @BindView(R.id.errorView)
    TextView errorView;
    @BindView(R.id.docView)
    RelativeLayout docView;
    private DocumentViewModel documentViewModel;
    private List<String> documentList;
    private List<DocumentModel> documentModelList;
    private boolean isFromLogin = false;
    RecyclerViewItemClickListener recyclerViewItemClickListener = new RecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            if (documentModelList != null) {
                Intent intent = new Intent(DocumentListActivity.this, UploadDocumentActivity.class);
                intent.putExtra(IntentConstants.DOCUMENT_EXTRA, getDocumentFromList(documentList.get(position), documentModelList));
                intent.putExtra(IntentConstants.DOC_TYPE_EXTRA, documentList.get(position));
                startActivity(intent);
            } else
                getDocumentsFromServer();
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
        isFromLogin = getIntent().getBooleanExtra(IntentConstants.IS_FROM_LOGIN, false);
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
    }

    @Override
    protected void onResume() {
        getDocumentsFromServer();
        super.onResume();
    }

    private void getDocumentsFromServer() {
        if (checkNetworkAvailableWithoutError()) {
            showProgressDialog(AppConstants.PLEASE_WAIT, false);
            documentViewModel.getDocumentRepository().getDocuments(documentViewModel.getDocumentsResponseMld());
        } else {
            docView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
        }
    }

    private void setDocumentsObserver() {
        documentViewModel.getDocumentsResponseMld().observe(this, documentsResponse -> {
            removeProgressDialog();
            if (isSuccessResponse(documentsResponse)) {
                documentModelList = documentsResponse.getDocumentsList();
                documentListAdapter.setDocumentModelList(documentModelList);
                documentListAdapter.notifyDataSetChanged();
                if (isFromLogin && documentsResponse.isDocumentCompleted()) {
                    UserModel userModel = appPreferences.getUserDetails();
                    userModel.documentCompleted = documentsResponse.isDocumentCompleted();
                    appPreferences.setUserDetails(userModel);
                    btn_continue.setVisibility(View.VISIBLE);
                }
                Toast.makeText(DocumentListActivity.this, documentsResponse.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                documentModelList = null;
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

    @OnClick(R.id.btn_continue)
    public void onContinueClick() {
        startActivity(new Intent(this, DashBoardActivity.class));
    }
}

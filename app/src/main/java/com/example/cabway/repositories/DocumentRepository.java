package com.example.cabway.repositories;

import android.arch.lifecycle.MutableLiveData;

import com.example.core.CommonModels.DocumentModel;
import com.example.core.responseModel.JsonResponse;
import com.example.core.restApi.ApiExecutor;

public class DocumentRepository {
    private static DocumentRepository instance;

    public static DocumentRepository getInstance() {
        if (instance == null) {
            instance = new DocumentRepository();

        }
        return instance;
    }


    public void uploadDocument(MutableLiveData<JsonResponse> uploadDocumentResponse, DocumentModel documentModel, String filePath) {
        ApiExecutor.uploadDocument(uploadDocumentResponse, documentModel, filePath);
    }

    public void getDocuments(MutableLiveData<JsonResponse> documentsResponse){
        ApiExecutor.getDocuments(documentsResponse);
    }
}

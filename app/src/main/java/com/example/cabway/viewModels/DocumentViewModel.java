package com.example.cabway.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.cabway.repositories.DocumentRepository;
import com.example.core.responseModel.JsonResponse;

public class DocumentViewModel extends ViewModel {
    private MutableLiveData<JsonResponse> documentUploadResponse;
    private MutableLiveData<JsonResponse> documentsResponse;
    private DocumentRepository documentRepository;
    public void init() {
        documentUploadResponse = new MutableLiveData<>();
        documentsResponse = new MutableLiveData<>();
        documentRepository = DocumentRepository.getInstance();
    }

    public MutableLiveData<JsonResponse> getDocumentUploadResponseMld() {
        return documentUploadResponse;
    }

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public MutableLiveData<JsonResponse> getDocumentsResponseMld() {
        return documentsResponse;
    }
}

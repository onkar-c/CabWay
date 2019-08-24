package com.example.core.responseModel;

import com.example.core.CommonModels.DocumentModel;
import com.example.core.CommonModels.UserModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JsonResponse implements Serializable {

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("heroes")
    private List<HeroesModel> heroesList;
    @SerializedName("user")
    private UserModel user;
    @SerializedName("listDocument")
    private List<DocumentModel> documentsList;

    public List<HeroesModel> getHeroesList() {
        return heroesList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUser() {
        return user;
    }

    public List<DocumentModel> getDocumentsList() {
        return documentsList;
    }
}

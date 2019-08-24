package com.example.core.CommonModels;

import java.io.Serializable;

public class DocumentModel implements Serializable {
    private String documentType;
    private String documentNumber;
    private String nameOnDocument;
    private String vehicleType;
    private String issueDate;
    private String expiryDate;
    private String stateName;
    private String gstNumber;
    private String imageUrl;
    private int userId;
    private int uuid;

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setNameOnDocument(String nameOnDocument) {
        this.nameOnDocument = nameOnDocument;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getNameOnDocument() {
        return nameOnDocument;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getStateName() {
        return stateName;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getUserId() {
        return userId;
    }

    public int getUuid() {
        return uuid;
    }
}

package com.example.core.requestModels;

public class DocumentUploadRequestModel {
    private String docType;
    private String cardNumber;
    private String nameOnCard;
    private String vehicleType;
    private String issueDate;
    private String expiryDate;
    private String stateName;
    private String gstNumber;

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
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
}

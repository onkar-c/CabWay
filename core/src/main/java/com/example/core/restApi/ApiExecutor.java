package com.example.core.restApi;

import androidx.lifecycle.MutableLiveData;

import com.example.core.ApiClient;
import com.example.core.ApiInterface;
import com.example.core.CommonModels.CityModel;
import com.example.core.CommonModels.DocumentModel;
import com.example.core.RequestExecutor;
import com.example.core.Utills.FileUtils;
import com.example.core.requestModels.CreateRideRequestModel;
import com.example.core.requestModels.LoginRequestModel;
import com.example.core.requestModels.ResetPasswordModel;
import com.example.core.requestModels.VerifyOtpRequestModel;
import com.example.core.responseModel.JsonResponse;
import com.google.gson.Gson;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ApiExecutor {

    private static ApiInterface getApiInterface() {
        return ApiClient.getClient().create(ApiInterface.class);
    }

    public static void getOtpForRegistration(final MutableLiveData<JsonResponse> mltOtpResponse, String mobileNo) {
        RequestExecutor.ExecuteApi(getApiInterface().getOtpForRegistration(mobileNo), mltOtpResponse);
    }

    public static void getOtpForForgetPassword(final MutableLiveData<JsonResponse> mltOtpResponse, String mobileNo) {
        RequestExecutor.ExecuteApi(getApiInterface().getOtpForForgetPassword(mobileNo), mltOtpResponse);
    }

    public static void verifyOTP(final MutableLiveData<JsonResponse> mltVerifyOtpResponse, VerifyOtpRequestModel verifyOtpRequestModel) {
        RequestExecutor.ExecuteApi(getApiInterface().verifyOTP(verifyOtpRequestModel), mltVerifyOtpResponse);
    }

    public static void resetPassword(final MutableLiveData<JsonResponse> mltResetPassword, ResetPasswordModel resetPasswordModel) {
        RequestExecutor.ExecuteApi(getApiInterface().resetPassword(resetPasswordModel), mltResetPassword);
    }

    public static void registerUser(final MutableLiveData<JsonResponse> mltRegisterUserResponse, com.example.core.CommonModels.UserModel userModel, String filePath) {
        RequestBody user = RequestBody.create(MultipartBody.FORM, new Gson().toJson(userModel));
        MultipartBody.Part body = FileUtils.getMultipartBody(filePath);
        RequestExecutor.ExecuteApi(getApiInterface().register(body, user), mltRegisterUserResponse);
    }

    public static void updateUser(final MutableLiveData<JsonResponse> mltUpdateUserResponse, com.example.core.CommonModels.UserModel userModel, String filePath) {
        RequestBody user = RequestBody.create(MultipartBody.FORM, new Gson().toJson(userModel));
        MultipartBody.Part body = FileUtils.getMultipartBody(filePath);
        RequestExecutor.ExecuteApi(getApiInterface().updateUser(body, user), mltUpdateUserResponse);
    }

    public static void validateLogin(final MutableLiveData<JsonResponse> mltLoginResponse, LoginRequestModel loginRequestModel) {
        RequestExecutor.ExecuteApi(getApiInterface().validateLogin(loginRequestModel), mltLoginResponse);
    }

    public static void uploadDocument(final MutableLiveData<JsonResponse> mltUploadDocumentResponse, DocumentModel documentModel, String filePath) {
        RequestBody document = RequestBody.create(MultipartBody.FORM, new Gson().toJson(documentModel));
        MultipartBody.Part body = FileUtils.getMultipartBody(filePath);
        RequestExecutor.ExecuteApi(getApiInterface().documentUpload(body, document), mltUploadDocumentResponse);
    }

    public static void getDocuments(final MutableLiveData<JsonResponse> mltDocumentsResponse) {
        RequestExecutor.ExecuteApi(getApiInterface().getDocuments(), mltDocumentsResponse);
    }

    public static void getStateAndCity(final MutableLiveData<JsonResponse> mltStateCityResponse, String dataVersion) {
        RequestExecutor.ExecuteApi(getApiInterface().getStateAndCity(dataVersion), mltStateCityResponse);
    }


    public static void createUpdateRide(final MutableLiveData<JsonResponse> mltCreateRideResponse, CreateRideRequestModel createRideRequestModel, boolean isUpdate) {
        if (isUpdate)
            RequestExecutor.ExecuteApi(getApiInterface().updateRide(createRideRequestModel), mltCreateRideResponse);
        else
            RequestExecutor.ExecuteApi(getApiInterface().createRide(createRideRequestModel), mltCreateRideResponse);
    }

    public static void deleteRide(final MutableLiveData<JsonResponse> mltDeleteRideResponse, Long rideId) {
        RequestExecutor.ExecuteApi(getApiInterface().deleteRide(rideId), mltDeleteRideResponse);
    }

    public static void requestRide(final MutableLiveData<JsonResponse> mltRequestRideResponse, Long rideId) {
        RequestExecutor.ExecuteApi(getApiInterface().requestRide(rideId), mltRequestRideResponse);
    }

    public static void updatePreferredCity(final MutableLiveData<JsonResponse> mltPreferredCityResponse, CityModel cityModel) {
        RequestExecutor.ExecuteApi(getApiInterface().updatePreferredCity(cityModel), mltPreferredCityResponse);
    }

    public static void logout(final MutableLiveData<JsonResponse> mltLogoutResponse) {
        RequestExecutor.ExecuteApi(getApiInterface().logout(), mltLogoutResponse);
    }

    public static void getRides(final MutableLiveData<JsonResponse> mltRidesResponse) {
        RequestExecutor.ExecuteApi(getApiInterface().getRides(), mltRidesResponse);
    }

    public static void acceptRejectRides(Long rideId, int driverId, String action, final MutableLiveData<JsonResponse> mltAcceptRejectRidesResponse) {
        RequestExecutor.ExecuteApi(getApiInterface().acceptRejectRide(rideId, driverId, action), mltAcceptRejectRidesResponse);
    }

    public static void getRidesHistory(final MutableLiveData<JsonResponse> mltRidesHistoryResponse) {
        RequestExecutor.ExecuteApi(getApiInterface().getRidesHistory(), mltRidesHistoryResponse);
    }
}

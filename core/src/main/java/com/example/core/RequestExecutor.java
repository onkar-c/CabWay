package com.example.core;

import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.example.core.responseModel.JsonResponse;
import com.example.database.Utills.AppConstants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestExecutor {


    public static void ExecuteApi(Call<JsonResponse> apiToBeCalled, final MutableLiveData<JsonResponse> mutableResponse) {

        apiToBeCalled.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponse> call,
                                   @NonNull Response<JsonResponse> response) {
                if (response.isSuccessful()) {
                    mutableResponse.setValue(response.body());
                } else
                    setErrorResponse(mutableResponse, AppConstants.SERVER_ISSUE);
            }

            @Override
            public void onFailure(@NonNull Call<JsonResponse> call, @NonNull Throwable t) {
                setErrorResponse(mutableResponse, AppConstants.SERVER_ISSUE);
            }
        });
    }

    private static void setErrorResponse(MutableLiveData<JsonResponse> mutableResponse, String message){
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatus(AppConstants.ERROR);
        jsonResponse.setMessage(message);
        mutableResponse.setValue(jsonResponse);

    }

   /* public static void execute(Observable<JsonResponse> call, final MutableLiveData<JsonResponse> response) {
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("API-Executor", "onSubscribe");
                    }

                    @Override
                    public void onNext(JsonResponse jsonResponse) {
                        response.setValue(jsonResponse);
                        Log.i("API-Executor", "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("API-Executor", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("API-Executor", "onComplete");

                    }
                });
    }*/
}

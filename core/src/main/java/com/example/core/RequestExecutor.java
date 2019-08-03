package com.example.core;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.core.responseModel.JsonResponse;
import com.example.database.Utills.AppConstants;

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
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonResponse> call, @NonNull Throwable t) {
                JsonResponse jsonResponse = new JsonResponse();
                jsonResponse.setStatus(AppConstants.ERROR);
                jsonResponse.setMessage(t.getMessage());
                mutableResponse.setValue(jsonResponse);
            }
        });
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

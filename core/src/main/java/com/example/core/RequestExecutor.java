package com.example.core;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.core.responseModel.JsonResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RequestExecutor {

    public static void execute(Observable<JsonResponse> call, final MutableLiveData<JsonResponse> response) {
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
    }
}

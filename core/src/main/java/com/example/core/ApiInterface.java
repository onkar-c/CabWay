package com.example.core;


import com.example.core.responseModel.JsonResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("db")
    Observable<JsonResponse> getHeros();
}

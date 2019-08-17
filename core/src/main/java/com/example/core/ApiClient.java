package com.example.core;

import android.support.annotation.NonNull;

import com.example.core.Utills.AppPreferences;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String IP = "192.168.43.36:9090";
    private static final String BASE_URL = "http://" + IP +"/cabsystem/";
//    private static final String DUMMY_BASE_URL = "https://my-json-server.typicode.com/onkar-c/DummyApi/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS);
            httpClient.addInterceptor(new Interceptor() {
                @NonNull
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                    String authKey = getAuthKey();
                    if(authKey != null && authKey.length() > 0 )
                        requestBuilder.header("authKey", authKey);
                    String webClientId = "ji";
                    requestBuilder.header("Client-Id", webClientId);
                    Request request = requestBuilder.method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }


    private static String getAuthKey(){
        String authKey = null;
        AppPreferences appPreferences = AppPreferences.getInstance();
        if(appPreferences != null){
            authKey = appPreferences.getAuthKey();
        }
        return authKey;
    }
}

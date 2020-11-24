package com.example.idillikaapp;

import android.app.Application;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient extends Application {

    private static ApiInterface apiService;
    private static String BASE_URL = "https://idillika.com/api/";

    @Override
    public void onCreate() {
        super.onCreate();


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();

        apiService = retrofit.create(ApiInterface.class);
    }



    public static ApiInterface getApiService() {
        return apiService;
    }
}

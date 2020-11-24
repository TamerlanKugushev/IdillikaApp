package com.example.idillikaapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("catalogList.php")
    Call<List<Product>> getCatalogList(
            @Query("section") Integer section,
            @Query("session_id") String id
    );
}

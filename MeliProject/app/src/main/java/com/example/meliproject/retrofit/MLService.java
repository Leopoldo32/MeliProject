package com.example.meliproject.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MLService {

    @Headers("Content-Type: application/json")
    @GET("search")
    Call<com.example.meliproject.retrofit.SearchResult> getMLSearchResult(@Query("q") String item);
}

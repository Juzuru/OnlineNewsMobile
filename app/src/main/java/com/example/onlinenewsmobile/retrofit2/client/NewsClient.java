package com.example.onlinenewsmobile.retrofit2.client;

import com.example.onlinenewsmobile.models.KA.SearchNewsDTO;
import com.example.onlinenewsmobile.models.KA.TopNewsHighReadTimes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsClient {
    @GET("news")
    Call<List<SearchNewsDTO>> findByLikeTitle(@Query("title") String title);

    @GET("news/top-readtimes")
    Call<List<TopNewsHighReadTimes>> getTopNewsHighReadTimes();
}

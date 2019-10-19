package com.example.onlinenewsmobile.retrofit2;

import com.example.onlinenewsmobile.retrofit2.client.NewsClient;

public class APIUtils {
    public static final String BASE_URL = "http://api.etutor.top:8021/NewsMobile/api/";

    public static NewsClient getNewsClient() {
        return RetrofitClient.getClient(BASE_URL).create(NewsClient.class);
    }
}

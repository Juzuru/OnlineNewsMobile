package com.example.onlinenewsmobile.retrofit2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    private static void intiRetrofit(String baseurl) {
        Retrofit retrofit = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(50000, TimeUnit.MILLISECONDS)
                .writeTimeout(50000, TimeUnit.MILLISECONDS)
                .connectTimeout(50000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
        Gson gson =
                new GsonBuilder()
                        .setLenient()
                        .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                        .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                        .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitClient.retrofit = retrofit;
    }

    public static Retrofit getClient(String baseurl) {
        if (RetrofitClient.retrofit == null) {
            intiRetrofit(baseurl);
        }
        return retrofit;
    }
}

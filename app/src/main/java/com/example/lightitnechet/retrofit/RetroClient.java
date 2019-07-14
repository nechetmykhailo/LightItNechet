package com.example.lightitnechet.retrofit;

import com.example.lightitnechet.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit getRetrofitInstance() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create());

        return builder.client(httpClient.build()).build();
    }

    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}

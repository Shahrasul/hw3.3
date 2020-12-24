package com.example.hw33.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherService {
    private static RetrofitApi retrofitApi;

    private WeatherService() {
    }

    public static RetrofitApi getInstance() {
        if (retrofitApi == null) {
            retrofitApi = buildRetrofit();
        }
        return retrofitApi;
    }

    private static RetrofitApi buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitApi.class);
    }
}
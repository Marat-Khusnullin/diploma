package com.example.arcoretest;

import retrofit2.Retrofit;

public class NetworkService {

    private static NetworkService mInstance;
    private static final String BASE_URL = "https://diploma-engineering-comm.herokuapp.com";
    private Retrofit mRetrofit;

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }
}

package com.example.arcoretest;

import java.util.List;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.GET;

public interface CityWebApi {

    @GET("/water_communication")
    public Call<List<WaterObject>> getWaterObjects();

}

package com.example.arcoretest;

import java.util.List;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CityWebApi {

    @GET("/water_communication")
    public Call<List<WaterObject>> getWaterObjects(@Header("x_pos") Double myLatitude,
                                                   @Header("y_pos") Double myLongitude);

}

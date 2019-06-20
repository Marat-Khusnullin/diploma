package com.example.arcoretest.interfaces;

import com.example.arcoretest.WaterObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CityWebApi {

    @GET("/water_communication")
    public Call<List<WaterObject>> getWaterObjects(@Header("x_pos") Double myLatitude,
                                                   @Header("y_pos") Double myLongitude);


    @GET("/water_communication")
    public Call<List<WaterObject>> getWaterObjectsFull();
    /*@GET("/data_communication")
    public Call<List<DataObject>> getDataObjects(@Header("x_pos") Double myLatitude,
                                                   @Header("y_pos") Double myLongitude);

    @GET("/gas_communication")
    public Call<List<GasObject>> getGasObjects(@Header("x_pos") Double myLatitude,
                                                 @Header("y_pos") Double myLongitude);

    @GET("/electricity_communication")
    public Call<List<ElectricityObject>> getElectricityObjects(@Header("x_pos") Double myLatitude,
                                               @Header("y_pos") Double myLongitude);*/


}

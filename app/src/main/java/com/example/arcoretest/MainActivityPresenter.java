package com.example.arcoretest;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.arcoretest.utils.ObjectsConverter;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    private MainActivity mainActivity;



    public MainActivityPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void activityIsReady() {
        NetworkService.getInstance().getCityWebApi().getWaterObjects(55.790612, 49.123078)
                .enqueue(new Callback<List<WaterObject>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<WaterObject>> call, Response<List<WaterObject>> response) {
                        Toast.makeText(mainActivity, "ПРИШЛО", Toast.LENGTH_SHORT).show();


                        ObjectsConverter objectsConverter = new ObjectsConverter(mainActivity.getzCoord());
                        mainActivity.setWaterObjectsToVrMap(objectsConverter.convertWaterObjects(response.body()));
                    }

                    @Override
                    public void onFailure(Call<List<WaterObject>> call, Throwable t) {
                        Log.i("First object", "ет не то");
                    }
                });


    }





}

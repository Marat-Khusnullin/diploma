package com.example.arcoretest;

import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.arcoretest.model.LocationClassModel;
import com.example.arcoretest.utils.ObjectsConverter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArObjectsFragmentPresenter {
    ArObjectsFragment fragment;




    public ArObjectsFragmentPresenter(ArObjectsFragment fragment) {
        this.fragment = fragment;
    }
    public void getLocalWaterObjects() {

        LocationClassModel locationClassModel = new LocationClassModel(fragment.getContext());
        locationClassModel.updateLocation();


    }

    public void setLocation(Location location) {
        NetworkService.getInstance().getCityWebApi().getWaterObjects(55.790612, 49.123078)
                .enqueue(new Callback<List<WaterObject>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<WaterObject>> call, Response<List<WaterObject>> response) {
                        //Toast.makeText(mainActivity, "ПРИШЛО", Toast.LENGTH_SHORT).show();
                        ObjectsConverter objectsConverter = new ObjectsConverter(-1);
                        fragment.setWaterObjectsToVrMap(objectsConverter.convertWaterObjects(response.body()));   ;
                    }

                    @Override
                    public void onFailure(Call<List<WaterObject>> call, Throwable t) {
                        Log.i("First object", "ет не то");
                    }
                });
    }



}

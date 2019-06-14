package com.example.arcoretest;

import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.arcoretest.model.LocationClassModel;
import com.example.arcoretest.utils.NetworkService;
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
        locationClassModel.setArObjectsFragmentPresenter(this);
        locationClassModel.updateLocation();
    }

    public void setLocation(Location location) {
        Toast.makeText(fragment.getActivity(), "ЗАПРОС ПОШЁЛ", Toast.LENGTH_SHORT).show();
        /*NetworkService.getInstance().getCityWebApi().getWaterObjects(55.7906, 49.1231)
                .enqueue(new Callback<List<WaterObject>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<WaterObject>> call, Response<List<WaterObject>> response) {
                        Toast.makeText(fragment.getActivity(), "ПРИШЛО", Toast.LENGTH_SHORT).show();
                        ObjectsConverter objectsConverter = new ObjectsConverter(-1);
                        //Log.i("RESPONSE", );
                        //fragment.setWaterObjectsToVrMap(objectsConverter.convertWaterObjects(response.body(),fragment.getActivity()));   ;
                    }

                    @Override
                    public void onFailure(Call<List<WaterObject>> call, Throwable t) {
                        Log.i("First object", "ет не то");
                    }
                });*/
        NetworkService.getInstance().getCityWebApi().getWaterObjectsFull()
                .enqueue(new Callback<List<WaterObject>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<WaterObject>> call, Response<List<WaterObject>> response) {
                        Toast.makeText(fragment.getActivity(), "ПРИШЛО", Toast.LENGTH_SHORT).show();
                        ObjectsConverter objectsConverter = new ObjectsConverter(-1);
                        //Log.i("RESPONSE", );
                        if(response.body().size() ==0)
                            Toast.makeText(fragment.getActivity(), "Пустое тело ответа", Toast.LENGTH_SHORT).show();
                        fragment.setWaterObjectsToVrMap(objectsConverter.convertWaterObjects(response.body(),fragment.getActivity()));   ;
                    }

                    @Override
                    public void onFailure(Call<List<WaterObject>> call, Throwable t) {
                        Log.i("First object", "ет не то");
                    }
                });
    }



}

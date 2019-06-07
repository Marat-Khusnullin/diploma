package com.example.arcoretest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.LinkedList;

public class ArObjectsFragment extends Fragment {
    ArFragment arFragment;
    Context context;
    private ButtonRectangle waterButton;
    private ButtonRectangle electricityButton;
    private ButtonRectangle dataButton;
    private ButtonRectangle gasButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ar_objects_fragment_layout, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments
        arFragment = (ArFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        waterButton = getActivity().findViewById(R.id.button_water);
        electricityButton = getActivity().findViewById(R.id.button_electricity);
        dataButton = getActivity().findViewById(R.id.button_data);
        gasButton = getActivity().findViewById(R.id.button_gas);

        /*waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //testBualt();
                if(!isActive) {
                    setWaterObjectsToVrMap(new LinkedList<>());
                    isActive = true;
                } else {
                    for (int i = 0; i <currentObjects.size(); i++) {
                        if(currentObjects.get(i).getScene()!=null)
                        currentObjects.get(i).getScene().onRemoveChild(currentObjects.get(i).getParent());
                        currentObjects.get(i).setRenderable(null);
                    }
                    currentObjects.clear();
                    isActive = false;
                }
            }
        });

        electricityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isActive) {
                    setElectricityObjectsToVrMap(new LinkedList<>());
                    isActive=true;
                } else {
                    for (int i = 0; i <currentObjects.size(); i++) {
                        if(currentObjects.get(i).getScene()!=null)
                        currentObjects.get(i).getScene().onRemoveChild(currentObjects.get(i).getParent());
                        currentObjects.get(i).setRenderable(null);
                    }
                    currentObjects.clear();
                    isActive = false;
                }
            }
        });

        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }








}

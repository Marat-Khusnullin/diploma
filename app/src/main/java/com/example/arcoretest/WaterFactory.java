package com.example.arcoretest;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Марат on 31.01.2019.
 */

public class WaterFactory {

    public static List<PolylineOptions> getWaterPolylineList(List<WaterObject> list){
        List returnList = new LinkedList();
        for (int i = 0; i <list.size(); i++) {
            returnList.add(new PolylineOptions().add(new LatLng(list.get(i).getStartCoordinateX(), list.get(i).getStartCoordinateY()),
                    new LatLng(list.get(i).getEndCoordinateX(), list.get(i).getEndCoordinateY())).color(Color.BLUE));
        }
        return returnList;
    }






}

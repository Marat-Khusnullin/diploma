package com.example.arcoretest.utils;

import android.content.Context;

import com.example.arcoretest.LocalWaterObject;
import com.example.arcoretest.WaterObject;
import com.google.ar.sceneform.math.Vector3;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.cos;

public class ObjectsConverter {
    private double mLant = 55.790612;
    private double mLong = 49.123078;

    private double northDif;
    private double coordNorth;
    private double longDif;
    private double coordLong;
    private float zCoordinate;

    private static final double LATITUDE_COEF = 111134.861111;
    private static final double LONGITUDE_COEF = 111321.377778;

    public ObjectsConverter(float zCoordinate){
        this.zCoordinate = zCoordinate;
    }

    public List<LocalWaterObject> convertWaterObjects(List<WaterObject> waterObjects, Context context) {
        LocalWaterObject localObject;
        List<LocalWaterObject> localWaterObjects = new LinkedList<>();
        CompassClass compassClass = new CompassClass(context);
        compassClass.refreshAngle();
        float angle = compassClass.getAngle();
        for (int i = 0; i < waterObjects.size(); i++) {
            localObject = new LocalWaterObject();

            northDif = waterObjects.get(i).getStartCoordinateX() - mLant;
            coordNorth = northDif * LATITUDE_COEF * cos(angle);

            longDif = waterObjects.get(i).getStartCoordinateY() - mLong;
            coordLong = cos(mLant)*LONGITUDE_COEF * longDif * cos(angle);

            localObject.setFullCoordinate(new Vector3((float) coordLong, 0 ,(float) coordNorth));

            northDif = waterObjects.get(i).getEndCoordinateX() - mLant;
            coordNorth = northDif * LATITUDE_COEF * cos(angle);

            longDif = waterObjects.get(i).getEndCoordinateY() - mLong;
            coordLong = cos(mLant)*LONGITUDE_COEF * longDif * cos(angle);

            localObject.setEndCoordinate(new Vector3((float) coordLong, 0, (float) coordNorth));

            localObject.setCardInfo(waterObjects.get(i));
            localWaterObjects.add(localObject);

        }

        return localWaterObjects;
    }






}

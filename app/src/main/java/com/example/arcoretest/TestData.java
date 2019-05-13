package com.example.arcoretest;

import com.google.ar.sceneform.math.Vector3;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TestData {

    private List<LocalWaterObject> testWaterObjects;
    private List<LocalElectricityObject> testElectricityObjects;
    private List<LocalDataObject> testDataObjects;
    private List<LocalGasObject> testGasObjects;


    public List<LocalWaterObject> getTestWaterObjects() {
        testWaterObjects = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            LocalWaterObject waterObject = new LocalWaterObject();
            float startX = (float) new Random().nextInt(4) -2;
            float startY = (float) new Random().nextInt(4) -2;
            waterObject.setFullCoordinate(new Vector3(startX, -1, startY));
            waterObject.setEndCoordinate(new Vector3(startX + new Random().nextInt(2)-2, -1, startY + new Random().nextInt(6)-2));
            waterObject.setDepth(new Random().nextInt(30));
            waterObject.setOwner("ЖилСтройОрг");
            waterObject.setType("Вода");
            waterObject.setWorkDate(new Date().toString());
            waterObject.setWorkInfo("Прокладка труб");
            testWaterObjects.add(waterObject);
        }

        return testWaterObjects;
    }

    public List<LocalElectricityObject> getTestElectricityObjects() {
        testElectricityObjects = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            LocalElectricityObject electricityObject = new LocalElectricityObject();
            float startX = (float) new Random().nextInt(4) -2;
            float startY = (float) new Random().nextInt(4) -2;
            electricityObject.setFullCoordinate(new Vector3(startX, -1, startY));
            electricityObject.setEndCoordinate(new Vector3(startX + new Random().nextInt(2)-2, -1, startY + new Random().nextInt(6)-2));
            electricityObject.setDepth(new Random().nextInt(30));
            electricityObject.setOwner("ЖилСтройОрг");
            electricityObject.setType("Электричество");
            electricityObject.setWorkDate(new Date().toString());
            electricityObject.setWorkInfo("Прокладка проводки");
            testElectricityObjects.add(electricityObject);
        }

        return testElectricityObjects;
    }

    public List<LocalDataObject> getTestDataObjects() {
        testDataObjects = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            LocalDataObject dataObject = new LocalDataObject();
            dataObject.setFullCoordinate(new Vector3((float) new Random().nextInt(6) - 6, -1, new Random().nextInt(8)-8));
            dataObject.setEndCoordinate(new Vector3((float) new Random().nextInt(6) -6, -1, new Random().nextInt(8)-8));
            dataObject.setDepth(new Random().nextInt(30));
            dataObject.setOwner("ЖилСтройОрг");
            dataObject.setType("DATA");
            dataObject.setWorkDate(new Date().toString());
            dataObject.setWorkInfo("Прокладка кабеля");
            testDataObjects.add(dataObject);
        }

        return testDataObjects;
    }

    public List<LocalGasObject> getTestGasObjects() {
        testGasObjects = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            LocalGasObject gasObject = new LocalGasObject();
            gasObject.setFullCoordinate(new Vector3((float) new Random().nextInt(6) - 6, -1, new Random().nextInt(8)-8));
            gasObject.setEndCoordinate(new Vector3((float) new Random().nextInt(6) -6, -1, new Random().nextInt(8)-8));
            gasObject.setDepth(new Random().nextInt(30));
            gasObject.setOwner("ЖилСтройОрг");
            gasObject.setType("Газ");
            gasObject.setWorkDate(new Date().toString());
            gasObject.setWorkInfo("Прокладка газопровода");
            testGasObjects.add(gasObject);
        }
        return testGasObjects;
    }



}

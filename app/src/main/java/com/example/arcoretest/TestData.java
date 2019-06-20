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
            float startX = (float) new Random().nextInt(4) -4;
            float startY = (float) new Random().nextInt(4) -4;
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
            float startX = (float) new Random().nextInt(4) -4;
            float startY = (float) new Random().nextInt(4) -4;
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
            float startX = (float) new Random().nextInt(4) -4;
            float startY = (float) new Random().nextInt(4) -4;
            dataObject.setFullCoordinate(new Vector3(startX, -1, startY));
            dataObject.setEndCoordinate(new Vector3(startX + new Random().nextInt(2)-2, -1, startY + new Random().nextInt(6)-2));
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
            float startX = (float) new Random().nextInt(4) -4;
            float startY = (float) new Random().nextInt(4) -4;
            gasObject.setFullCoordinate(new Vector3(startX, -1, startY));
            gasObject.setEndCoordinate(new Vector3(startX + new Random().nextInt(2)-2, -1, startY + new Random().nextInt(6)-2));
            gasObject.setDepth(new Random().nextInt(30));
            gasObject.setOwner("ЖилСтройОрг");
            gasObject.setType("Газ");
            gasObject.setWorkDate(new Date().toString());
            gasObject.setWorkInfo("Прокладка газопровода");
            testGasObjects.add(gasObject);
        }
        return testGasObjects;
    }

    public List<WaterObject> getTestServerWaterObjects() {
        List<WaterObject> objects = new LinkedList<WaterObject>();
        WaterObject waterObject = new WaterObject();
        double startX = 55.791876;
        double startY = 49.122555;
        waterObject.setStartCoordinate(55.791876, 49.122555);
        waterObject.setEndCoordinate(55.791977, 49.122757);
        objects.add(waterObject);

        WaterObject waterObject1 = new WaterObject();
        waterObject1.setStartCoordinate(55.791977, 49.122757);
        waterObject1.setEndCoordinate(55.792083, 49.122641);
        objects.add(waterObject1);

        WaterObject waterObject2 = new WaterObject();
        waterObject2.setStartCoordinate(55.792083, 49.122641);
        waterObject2.setEndCoordinate(55.792140, 49.121835);
        objects.add(waterObject2);

        WaterObject waterObject3 = new WaterObject();
        waterObject3.setStartCoordinate(55.792140, 49.121835);
        waterObject3.setEndCoordinate(55.792292, 49.121714);
        objects.add(waterObject3);

        WaterObject waterObject4 = new WaterObject();
        waterObject4.setStartCoordinate(55.792292, 49.121714);
        waterObject4.setEndCoordinate(55.792868, 49.122343);
        objects.add(waterObject4);

        return objects;

    }





}

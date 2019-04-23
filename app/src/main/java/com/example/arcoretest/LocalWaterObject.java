package com.example.arcoretest;

import com.google.ar.sceneform.math.Vector3;

public class LocalWaterObject {

    private float xCoorditate;
    private float yCoordinate;
    private float zCoordinate;
    private Vector3 fullCoordinate;



    private String type;
    private String owner;
    private int depth;
    private String workInfo;
    private String workDate;


    public float getxCoorditate() {
        return xCoorditate;
    }

    public void setxCoorditate(float xCoorditate) {
        this.xCoorditate = xCoorditate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public float getzCoordinate() {
        return zCoordinate;
    }

    public void setzCoordinate(float zCoordinate) {
        this.zCoordinate = zCoordinate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getWorkInfo() {
        return workInfo;
    }

    public void setWorkInfo(String workInfo) {
        this.workInfo = workInfo;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public Vector3 getFullCoordinate() {
        return fullCoordinate;
    }

    public void setFullCoordinate(Vector3 fullCoordinate) {
        this.fullCoordinate = fullCoordinate;
    }

    public void setCardInfo(WaterObject object){
        type = object.getType();
        owner = object.getOwner();
        depth = object.getDepth();
        workInfo = object.getWorkInfo();
        workDate = object.getWorkDate();
    }
}

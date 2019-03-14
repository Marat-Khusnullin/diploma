package com.example.arcoretest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaterObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("start_coordinate_x")
    @Expose
    private Double startCoordinateX;
    @SerializedName("start_coordinate_y")
    @Expose
    private Double startCoordinateY;
    @SerializedName("end_coordinate_x")
    @Expose
    private Double endCoordinateX;
    @SerializedName("end_coordinate_y")
    @Expose
    private Double endCoordinateY;
    @SerializedName("depth")
    @Expose
    private Integer depth;
    @SerializedName("work_info")
    @Expose
    private String workInfo;
    @SerializedName("work_date")
    @Expose
    private String workDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Double getStartCoordinateX() {
        return startCoordinateX;
    }

    public void setStartCoordinateX(Double startCoordinateX) {
        this.startCoordinateX = startCoordinateX;
    }

    public Double getStartCoordinateY() {
        return startCoordinateY;
    }

    public void setStartCoordinateY(Double startCoordinateY) {
        this.startCoordinateY = startCoordinateY;
    }

    public Double getEndCoordinateX() {
        return endCoordinateX;
    }

    public void setEndCoordinateX(Double endCoordinateX) {
        this.endCoordinateX = endCoordinateX;
    }

    public Double getEndCoordinateY() {
        return endCoordinateY;
    }

    public void setEndCoordinateY(Double endCoordinateY) {
        this.endCoordinateY = endCoordinateY;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
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


}

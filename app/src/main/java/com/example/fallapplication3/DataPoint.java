package com.example.fallapplication3;

public class DataPoint {
    private String timestamp;
    private double accelX;
    private double accelY;
    private double accelZ;
    private String activityLabel;

    public DataPoint(String timestamp, double accelX, double accelY, double accelZ, String activityLabel) {
        this.timestamp = timestamp;
        this.accelX = accelX;
        this.accelY = accelY;
        this.accelZ = accelZ;
        this.activityLabel = activityLabel;
    }

    // getters and setters for each property

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getAccelX() {
        return accelX;
    }

    public void setAccelX(double accelX) {
        this.accelX = accelX;
    }

    public double getAccelY() {
        return accelY;
    }

    public void setAccelY(double accelY) {
        this.accelY = accelY;
    }

    public double getAccelZ() {
        return accelZ;
    }

    public void setAccelZ(double accelZ) {
        this.accelZ = accelZ;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }
}
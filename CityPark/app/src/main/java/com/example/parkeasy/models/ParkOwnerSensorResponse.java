package com.example.parkeasy.models;

public class ParkOwnerSensorResponse {
    private boolean error;
    private String message;
    private SensorData sensorData;

    public ParkOwnerSensorResponse(boolean error, String message, SensorData sensorData) {
        this.error = error;
        this.message = message;
        this.sensorData = sensorData;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public SensorData getSensorData() {
        return sensorData;
    }
}

package com.example.parkeasy.models;

public class ParkStatusResponse {
    private boolean error;
    private String message;
    private ParkStatus parkStatus;

    public ParkStatusResponse(boolean error, String message, ParkStatus parkStatus) {
        this.error = error;
        this.message = message;
        this.parkStatus = parkStatus;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ParkStatus getParkStatus() {
        return parkStatus;
    }
}

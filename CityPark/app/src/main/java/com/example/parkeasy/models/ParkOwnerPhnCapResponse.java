package com.example.parkeasy.models;

public class ParkOwnerPhnCapResponse {
    private boolean error;
    private String message;
    private ParkOwnerPhnCap result;

    public ParkOwnerPhnCapResponse(boolean error, String message, ParkOwnerPhnCap result) {
        this.error = error;
        this.message = message;
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ParkOwnerPhnCap getResult() {
        return result;
    }
}

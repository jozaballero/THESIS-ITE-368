package com.example.parkeasy.models;

import java.util.List;

public class ParkListResponse {
    private boolean error;
    private String message;
    private List<ParkList> parkList;

    public ParkListResponse(boolean error, List<ParkList> parkList) {
        this.error = error;
        this.parkList = parkList;
    }

    public boolean isError() {
        return error;
    }



    public List<ParkList> getParkList() {
        return parkList;
    }
}

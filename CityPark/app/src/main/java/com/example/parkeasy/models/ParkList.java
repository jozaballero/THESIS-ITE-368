package com.example.parkeasy.models;

import java.sql.Timestamp;

public class ParkList {
    private String park_name, phone, address, capacity,price, map_location;
    private double latitude, longitude;

    public ParkList(String park_name, String phone, String price, String capacity, String map_location, double latitude, double longitude) {
        this.park_name = park_name;
        this.phone = phone;
        this.price = price;
        this.capacity = capacity;
        this.map_location = map_location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPark_name() {
        return park_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getPrice() {
        return price;
    }

    public String getMap_location() {
        return map_location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

package com.example.parkeasy.models;

public class ParkOwnerPhnCap {
    private String map_location;
    private String phone;
    private String price;
    private String capacity;

    public ParkOwnerPhnCap(String phone,String price, String capacity,String map_location) {
        this.phone = phone;
        this.capacity = capacity;
        this.map_location = map_location;
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public String getCapacity() {
        return capacity;
    }
    public String getPrice() {
        return price;
    }
    public String getMap_location() {return map_location;}
}

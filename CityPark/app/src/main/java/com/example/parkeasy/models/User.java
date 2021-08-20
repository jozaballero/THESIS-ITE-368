package com.example.parkeasy.models;

public class User {
    private int id;
    private String fullName, phoneNum, address, vehicleType, vehicleRegNo;

    public User(int id, String fullName, String phoneNum, String address, String vehicleType, String vehicleRegNo) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.vehicleType = vehicleType;
        this.vehicleRegNo = vehicleRegNo;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }
}

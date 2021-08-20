package com.example.parkeasy.models;

import java.sql.Time;
import java.util.Date;

public class ParkStatus {
    private String approval_status;
    private String park_name;
    private String date;
    private String time;

    public ParkStatus(String approval_status, String park_name, String date, String time) {
        this.approval_status = approval_status;
        this.park_name = park_name;
        this.date = date;
        this.time = time;
    }

    public String getApproval_status() {
        return approval_status;
    }

    public String getPark_name() {
        return park_name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}

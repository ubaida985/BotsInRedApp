package com.example.botsinred.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ScheduleModel {
    String time;
    String name;
    HashMap<String, Integer> pills;
    Date date;

    public ScheduleModel() {
    }

    public ScheduleModel(String time, String name, HashMap<String, Integer> pills, Date date) {
        this.time = time;
        this.name = name;
        this.pills = pills;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getPills() {
        return pills;
    }

    public void setPills(HashMap<String, Integer> pills) {
        this.pills = pills;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

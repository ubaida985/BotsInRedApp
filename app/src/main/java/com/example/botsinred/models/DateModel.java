package com.example.botsinred.models;

public class DateModel {
    String day;
    String date;

    public DateModel(String day, String date) {
        this.day = day;
        this.date = date;
    }

    public DateModel() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.example.botsinred.models;

public class DoseModel {
    int doseID;
    String doseName;
    int doseQuantity;
    String doseDescription;
    String day;
    String date;
    String month;
    String hours;
    String minutes;

    public DoseModel(int doseID, String doseName, int doseQuantity, String doseDescription, String day, String date, String month, String hours, String minutes) {
        this.doseID = doseID;
        this.doseName = doseName;
        this.doseQuantity = doseQuantity;
        this.doseDescription = doseDescription;
        this.day = day;
        this.date = date;
        this.month = month;
        this.hours = hours;
        this.minutes = minutes;
    }

    public DoseModel() {
    }

    public int getDoseID() {
        return doseID;
    }

    public void setDoseID(int doseID) {
        this.doseID = doseID;
    }

    public String getDoseName() {
        return doseName;
    }

    public void setDoseName(String doseName) {
        this.doseName = doseName;
    }

    public int getDoseQuantity() {
        return doseQuantity;
    }

    public void setDoseQuantity(int doseQuantity) {
        this.doseQuantity = doseQuantity;
    }

    public String getDoseDescription() {
        return doseDescription;
    }

    public void setDoseDescription(String doseDescription) {
        this.doseDescription = doseDescription;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }
}


package com.example.botsinred.models;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@IgnoreExtraProperties
public class ScheduleModel {
    String userID;
    String scheduleID;
    String time;
    String name;
    ArrayList<CategoryModel> categories;
    String doseDate;
    @ServerTimestamp Date date;
    boolean completed = false;


    /**
     * DOSE
     * Time, name, list of categories
     *
     * CAT
     * list of pills and qty
     */



    public ScheduleModel() {
    }

    public ScheduleModel(String userID, String scheduleID, String time, String name, ArrayList<CategoryModel> categories, Date date, String doseDate) {
        this.time = time;
        this.name = name;
        this.categories = categories;
        this.date = date;
        this.doseDate = doseDate;
    }
    public ScheduleModel( String time, String name, ArrayList<CategoryModel> categories, Date date, String doseDate) {
        this.time = time;
        this.name = name;
        this.categories = categories;
        this.date = date;
        this.doseDate = doseDate;
    }


    public String getDoseDate() {
        return doseDate;
    }

    public void setDoseDate(String doseDate) {
        this.doseDate = doseDate;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public ArrayList<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryModel> categories) {
        this.categories = categories;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ScheduleModel{" +
                "userID='" + userID + '\'' +
                ", scheduleID='" + scheduleID + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                ", date=" + date +
                ", completed=" + completed +
                '}';
    }
}

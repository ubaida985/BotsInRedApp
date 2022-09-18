package com.example.botsinred.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ScheduleModel {
    String time;
    String name;
    ArrayList<CategoryModel> categories;
    Date date;

    /**
     * DOSE
     * Time, name, list of categories
     *
     * CAT
     * list of pills and qty
     */

    public ScheduleModel() {
    }

    public ScheduleModel(String time, String name, ArrayList<CategoryModel> categories, Date date) {
        this.time = time;
        this.name = name;
        this.categories = categories;
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
}

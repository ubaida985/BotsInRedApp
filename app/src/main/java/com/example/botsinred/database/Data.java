package com.example.botsinred.database;

import androidx.annotation.Nullable;

import com.example.botsinred.models.CategoryModel;
import com.example.botsinred.models.ScheduleModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Data {
    static ArrayList<ScheduleModel> schedule = null;

    public Data() {
    }

    public Data(ArrayList<ScheduleModel> schedule, ArrayList<CategoryModel> categories) {
        this.schedule = schedule;
    }

    public ArrayList<ScheduleModel> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<ScheduleModel> schedule) {
        this.schedule = schedule;
    }

}

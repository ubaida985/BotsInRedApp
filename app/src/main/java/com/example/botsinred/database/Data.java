package com.example.botsinred.database;

import androidx.annotation.Nullable;

import com.example.botsinred.models.CategoryModel;
import com.example.botsinred.models.ScheduleModel;
import com.example.botsinred.models.UserModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Data {
    static ArrayList<ScheduleModel> schedule = null;
    static ArrayList<UserModel> users = null;
    static UserModel user = null;

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

    public static ArrayList<UserModel> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<UserModel> users) {
        Data.users = users;
    }

    public static UserModel getUser() {
        return user;
    }

    public static void setUser(UserModel user) {
        Data.user = user;
    }
}

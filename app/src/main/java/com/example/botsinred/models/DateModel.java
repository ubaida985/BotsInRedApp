package com.example.botsinred.models;

import java.util.Date;

public class DateModel {
   Date date;
   String dayOnDate, day;

    public DateModel() {
    }

    public DateModel(Date date) {
        this.date = date;

        String[] dateContainer = date.toString().split(" ");
        dayOnDate = dateContainer[0];
        day = dateContainer[2];
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDayOnDate() {
        return dayOnDate;
    }

    public void setDayOnDate(String dayOnDate) {
        this.dayOnDate = dayOnDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

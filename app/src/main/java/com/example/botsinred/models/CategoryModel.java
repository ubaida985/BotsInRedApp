package com.example.botsinred.models;

import java.util.HashMap;

public class CategoryModel {
    String categoryName;
    HashMap<String, Integer> pills;

    public CategoryModel() {
    }

    public CategoryModel(String categoryName, HashMap<String, Integer> pills) {
        this.categoryName = categoryName;
        this.pills = pills;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public HashMap<String, Integer> getPills() {
        return pills;
    }

    public void setPills(HashMap<String, Integer> pills) {
        this.pills = pills;
    }
}

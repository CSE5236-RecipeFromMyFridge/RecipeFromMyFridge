package com.example.recipefrommyfridgeapp.model;

import java.util.Locale;

public class Ingredient {

    public String name;
    public String zhName;
    public String amount;
    public boolean mSelected;

    public Ingredient() {
        mSelected = false;
    }

    public Ingredient(String name) {
        this.name = name;
        mSelected = false;
    }

    public Ingredient(String name, String zhName, String amount) {
        this.name = name;
        this.zhName = zhName;
        this.amount = amount;
        mSelected = false;
    }

    public String getName() {
        if (Locale.getDefault().toString().contains("en_US")) {
            return name;
        } else {
            return zhName;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }
}

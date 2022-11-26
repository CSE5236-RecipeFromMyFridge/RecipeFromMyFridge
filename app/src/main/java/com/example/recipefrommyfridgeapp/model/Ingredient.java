package com.example.recipefrommyfridgeapp.model;

import java.util.Locale;

public class Ingredient {

    public String enName;
    public String zhName;
    public String amount;
    public boolean mSelected;

    public Ingredient() {
        mSelected = false;
    }

    public Ingredient(String enName) {
        this.enName = enName;
        mSelected = false;
    }

    public Ingredient(String enName, String zhName, String amount) {
        this.enName = enName;
        this.zhName = zhName;
        this.amount = amount;
        mSelected = false;
    }

    public String getName() {
        if (Locale.getDefault().toString().contains("en_US")) {
            return enName;
        } else {
            return zhName;
        }
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
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

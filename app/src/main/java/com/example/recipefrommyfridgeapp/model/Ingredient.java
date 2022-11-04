package com.example.recipefrommyfridgeapp.model;

public class Ingredient {

    public String name;
    public String amount;
    public boolean mSelected;

    public Ingredient() {
        mSelected = false;
    }

    public Ingredient(String name) {
        this.name = name;
        mSelected = false;
    }

    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
        mSelected = false;
    }

    public String getName() {
        return name;
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

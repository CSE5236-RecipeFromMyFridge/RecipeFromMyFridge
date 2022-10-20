package com.example.recipefrommyfridgeapp.model;

public class Cuisine {

    public String name, type;

    public Cuisine(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Cuisine() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

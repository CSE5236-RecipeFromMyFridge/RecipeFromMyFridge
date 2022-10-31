package com.example.recipefrommyfridgeapp.model;

public class Recipe {

    public int cuisineId;
    public String name, content;
    public float rating;

    public Recipe(int cuisineId, String name, String content, float rating) {
        cuisineId = cuisineId;
        this.name = name;
        this.content = content;
        this.rating = rating;
    }

    public Recipe() {
    }

    public Recipe(String name, String content, float rating) {
        this.name = name;
        this.content = content;
        this.rating = rating;
    }


    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        cuisineId = cuisineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

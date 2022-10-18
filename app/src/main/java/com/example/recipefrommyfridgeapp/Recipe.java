package com.example.recipefrommyfridgeapp;

public class Recipe {

    private int mCuisineId;
    private String name;
    private String content;
    private float rating;

    public Recipe(int cuisineId, String name, String content, float rating) {
        mCuisineId = cuisineId;
        this.name = name;
        this.content = content;
        this.rating = rating;
    }

    public Recipe() {
    }


    public int getCuisineId() {
        return mCuisineId;
    }

    public void setCuisineId(int cuisineId) {
        mCuisineId = cuisineId;
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

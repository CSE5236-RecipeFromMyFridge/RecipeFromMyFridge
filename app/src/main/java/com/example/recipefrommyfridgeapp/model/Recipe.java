package com.example.recipefrommyfridgeapp.model;

import java.util.List;

public class Recipe {

    public String cuisineId;
    public String name, content;
    public float rating;
    public List<Ingredient> mIngredients;

    public Recipe(String cuisineId, String name, String content, float rating, List<Ingredient> ingredients) {
        this.cuisineId = cuisineId;
        this.name = name;
        this.content = content;
        this.rating = rating;
        this.mIngredients = ingredients;
    }

    public Recipe() {
    }


    public String getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(String cuisineId) {
        this.cuisineId = cuisineId;
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

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }
}

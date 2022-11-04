package com.example.recipefrommyfridgeapp.model;

import java.util.Map;

public class Recipe {

    public String cuisineId;
    public String name, content;
    public float rating;
    public Map<String, Ingredient> ingredients;

    public Recipe(String cuisineId, String name, String content, float rating, Map<String, Ingredient> ingredients) {
        this.cuisineId = cuisineId;
        this.name = name;
        this.content = content;
        this.rating = rating;
        this.ingredients = ingredients;
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

    public Map<String, Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean hasIngredient(String[] ingredients) {
        boolean contains = false;
        for (String ingredient : ingredients) {
            if (this.ingredients.containsKey(ingredient)) {
                contains = true;
            }
        }
        return contains;
    }
}

package com.example.recipefrommyfridgeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.recipefrommyfridgeapp.viewmodel.RecipeViewModel;

public class MenuActivity extends AppCompatActivity {

    private RecipeViewModel mRecipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}
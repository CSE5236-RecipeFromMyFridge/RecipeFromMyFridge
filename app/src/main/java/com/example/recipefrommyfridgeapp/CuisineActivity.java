package com.example.recipefrommyfridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeActivity;
import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeFragment;

public class CuisineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);
        final Button generateRecipeButton = (Button) findViewById(R.id.button_generate_recipe);
        generateRecipeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CuisineActivity.this, ChooseRecipeActivity.class);
                startActivity(intent);
            }
        });
    }
}
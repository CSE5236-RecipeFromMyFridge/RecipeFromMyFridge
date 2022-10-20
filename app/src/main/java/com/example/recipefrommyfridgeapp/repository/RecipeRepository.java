package com.example.recipefrommyfridgeapp.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.DataLoadingListener;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecipeRepository {
    static RecipeRepository instance;
    private List<Recipe> recipes;

    static Context mContext;
    static DataLoadingListener mDataLoadingListener;

    public static RecipeRepository getInstance(Context context){
        mContext = context;
        if (instance == null){
            instance = new RecipeRepository();
        }
        mDataLoadingListener = (DataLoadingListener) mContext;
        return instance;
    }

    public MutableLiveData<List<Recipe>> getRecipes(){
        loadRecipes();
        MutableLiveData<List<Recipe>> recipe = new MutableLiveData<>();
        recipe.setValue(recipes);
        return recipe;
    }

    private void loadRecipes(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Recipe");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    recipes.add(snapshot.getValue(Recipe.class));
                }
                mDataLoadingListener.onRecipeLoaded();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

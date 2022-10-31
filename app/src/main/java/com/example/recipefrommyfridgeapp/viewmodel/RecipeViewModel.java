package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.repository.AppRepository;
import com.example.recipefrommyfridgeapp.repository.RecipeRepository;
import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeViewModel extends AndroidViewModel {
    private AppRepository mAppRepository;
    private FirebaseRecyclerOptions<Recipe> options;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
    }

    public void createRecipe(String name, String content, Float rating){
        mAppRepository.createRecipe(name, content, rating);
    }

    public FirebaseRecyclerOptions<Recipe> retrieveRecipes(){
        options = mAppRepository.retrieveRecipes();
        return options;
    }

    public void deleteRecipe(String id){
        mAppRepository.deleteRecipe(id);
    }

    public void updateRecipe(String id, Map<String, Object> newRecipe){
        mAppRepository.updateRecipe(id, newRecipe);
    }
}

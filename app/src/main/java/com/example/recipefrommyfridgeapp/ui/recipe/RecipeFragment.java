package com.example.recipefrommyfridgeapp.ui.recipe;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Ingredient;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.viewmodel.LoggedInViewModel;
import com.example.recipefrommyfridgeapp.viewmodel.RecipeViewModel;
import com.example.recipefrommyfridgeapp.viewmodel.SavedRecipeViewModel;

import java.util.List;
import java.util.Map;

public class RecipeFragment extends Fragment implements SensorEventListener {

    private TextView recipeName, recipeType, recipeRating, recipeContent, recipeIngredient;
    private ImageButton mPreviousButton, mNextButton;
    private Button saveButton;

    private RecipeViewModel mRecipeViewModel;
    private SavedRecipeViewModel mSavedRecipeViewModel;
    private LoggedInViewModel mLoggedInViewModel;

    private String id;
    private String user;

    private SensorManager sm;
    private Sensor mSensor;

//    public RecipeFragment(String userId, String recipeId) {
//        id = "" + recipeId;
//        user = "" + userId;
//    }

    public RecipeFragment() {}

    public static RecipeFragment newInstance(String userId, String recipeId){
        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("recipeId", recipeId);
        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "RecipeFragment.onCreate()");
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mSavedRecipeViewModel = new ViewModelProvider(this).get(SavedRecipeViewModel.class);
        mLoggedInViewModel = new ViewModelProvider(this).get(LoggedInViewModel.class);
        id = getArguments().getString("recipeId");
        String recipeId = "" + id;
        Log.d("checkpoint5", "RecipeFragment.onCreate(): " + recipeId);
        mRecipeViewModel.getCurrentRecipe(recipeId);
        mRecipeViewModel.retrieveRecipeIdList();
        user = getArguments().getString("userId");
        mSavedRecipeViewModel.userSavedRecipe(user);

        sm = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        Log.d("checkpoint5", mSensor.getName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeName = v.findViewById(R.id.fragment_recipe_name);
        recipeType = v.findViewById(R.id.fragment_recipe_type);
        recipeRating = v.findViewById(R.id.fragment_recipe_rating);
        recipeContent = v.findViewById(R.id.fragment_recipe_content);
        recipeIngredient = v.findViewById(R.id.fragment_recipe_ingredients);
        mPreviousButton = v.findViewById(R.id.fragment_recipe_previous_button);
        mNextButton = v.findViewById(R.id.fragment_recipe_next_button);
        saveButton = v.findViewById(R.id.fragment_recipe_save);

        mLoggedInViewModel.getLoggedOutMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut){
                    saveButton.setEnabled(false);
                }
            }
        });

        mRecipeViewModel.getRecipeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null){
                    recipeName.setText(String.format("%s %s",
                            getResources().getString(R.string.field_name), recipe.getName()));
                    recipeType.setText(String.format("%s %s",
                            getResources().getString(R.string.field_cuisine_type), recipe.getCuisineId()));
                    recipeRating.setText(String.format("%s %s",
                            getResources().getString(R.string.field_rating), recipe.getRating()));
                    recipeContent.setText(String.format("%s %s",
                            getResources().getString(R.string.field_content), recipe.getContent()));
                    Map<String, Ingredient> ingredientMap = recipe.getIngredients();
                    StringBuilder output = new StringBuilder();
                    output.append(getResources().getString(R.string.field_ingredients) + " ");
                    for (Ingredient current : ingredientMap.values()) {
                        Log.d("checkpoint5", current.getName());
                        output.append(current.getName());
                        output.append(" - ");
                        output.append(current.getAmount());
                        output.append(" ");
                    }
                    recipeIngredient.setText(output);
                }
            }
        });

        mRecipeViewModel.getRecipeListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> ids) {
                if (ids.size() != 0){
                    final int[] idx = {ids.indexOf(id)};
                    mPreviousButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            idx[0] = (idx[0] - 1) % ids.size();
                            if (idx[0] < 0) {
                                idx[0] = ids.size() - 1;
                            }
                            updateRecipe(ids, idx);
                        }
                    });
                    mNextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            idx[0] = (idx[0] + 1) % ids.size();
                            updateRecipe(ids, idx);
                        }
                    });
                }
            }
        });

        mSavedRecipeViewModel.getUserSavedRecipeListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (strings.contains(id)){
                    saveButton.setEnabled(false);
                } else {
                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSavedRecipeViewModel.saveRecipe(user, id);
                        }
                    });
                }
            }
        });

        return v;
    }

    private void updateRecipe(List<String> ids, int[] idx){
        getParentFragmentManager().beginTransaction()
                .remove(RecipeFragment.this)
                .commit();
        getParentFragmentManager().popBackStack();
        Fragment fragment = newInstance(user, ids.get(idx[0]));
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setReorderingAllowed(true)
                .addToBackStack("Get recipe details")
                .commit();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        double curValue = magnitude(values[0], values[1], values[2]);
        if (curValue > 40){
            Log.d("checkpoint5", "onSensorChanged.curValue > 40");
            mRecipeViewModel.getRecipeListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> ids) {
                    if (ids.size() != 0){
                        final int[] idx = {ids.indexOf(id)};
                        idx[0] = (idx[0] + 1) % ids.size();
                        updateRecipe(ids, idx);
                    }
                }
            });
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public double magnitude(float x, float y, float z){
        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(this);
    }
}
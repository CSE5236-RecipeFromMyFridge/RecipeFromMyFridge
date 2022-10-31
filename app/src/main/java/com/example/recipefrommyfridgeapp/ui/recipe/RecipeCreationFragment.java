package com.example.recipefrommyfridgeapp.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.viewmodel.CuisineViewModel;
import com.example.recipefrommyfridgeapp.viewmodel.LoginRegisterViewModel;
import com.example.recipefrommyfridgeapp.viewmodel.RecipeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeCreationFragment extends Fragment implements View.OnClickListener {

    private Button createRecipeButton, backButton;
    private EditText name, content, rating;
    private Spinner spinner;
    private ProgressBar progressBar;

    private RecipeViewModel mRecipeViewModel;
    private CuisineViewModel mCuisineViewModel;

    private List<String> names;
    private String cuisineIdChosen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "RecipeCreationFragment.onCreate()");
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mCuisineViewModel = new ViewModelProvider(this).get(CuisineViewModel.class);
        mCuisineViewModel.retrieveCuisines();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_recipe_creation, container, false);
        createRecipeButton = v.findViewById(R.id.createRecipePage);
        backButton = v.findViewById(R.id.back_generate);
        name = v.findViewById(R.id.recipe_name);
        content = v.findViewById(R.id.recipe_content);
        rating = v.findViewById(R.id.recipe_rating);
        spinner = v.findViewById(R.id.recipe_spinner);
        progressBar = v.findViewById(R.id.progress_circular);
        createRecipeButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        names = new ArrayList<>();
        mCuisineViewModel.getCuisineMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Cuisine>>() {
            @Override
            public void onChanged(List<Cuisine> cuisines) {
                for (int i = 0; i < cuisines.size(); i++){
                    names.add(cuisines.get(i).getType());
                }
                ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, names);
                mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(mArrayAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        cuisineIdChosen = names.get(position);
                        Toast.makeText(getContext(), cuisineIdChosen, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });


        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_generate:
                getParentFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
                getParentFragmentManager().popBackStack();
                break;
            case R.id.createRecipePage:
                createRecipe();
                break;
        }
    }

    private void createRecipe(){
        String recipeName = name.getText().toString().trim();
        String recipeContent = content.getText().toString().trim();
        String recipeRating = rating.getText().toString();
        String cuisineId = cuisineIdChosen;
        Log.d("checkpoint5", cuisineId);
        if (recipeName.isEmpty()){
            name.setError("Recipe Name is required!");
            name.requestFocus();
            return;
        }
        if (recipeContent.isEmpty()){
            content.setError("Recipe Content is required!");
            content.requestFocus();
            return;
        }
        if (recipeRating.isEmpty()){
            rating.setError("Recipe Rating is required!");
            rating.requestFocus();
            return;
        }
        if (cuisineId.isEmpty()){
            Toast.makeText(getContext(), "Cuisine selection is required!", Toast.LENGTH_SHORT).show();
            spinner.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mRecipeViewModel.createRecipe(cuisineId, recipeName, recipeContent, Float.parseFloat(recipeRating));
        progressBar.setVisibility(View.GONE);


    }
}

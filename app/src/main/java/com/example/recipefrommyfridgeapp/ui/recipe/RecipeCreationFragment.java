package com.example.recipefrommyfridgeapp.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private DatabaseReference ref;
    private List<String> names;

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
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Cuisines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()){
                    Cuisine single = post.getValue(Cuisine.class);
                    String spinnerName = single.getType();
                    names.add(spinnerName);
                }
                ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, names);
                mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(mArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        String type = spinner.getSelectedItem().toString();
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
        if (type.isEmpty()){
            Toast.makeText(getContext(), "Cuisine selection is required!", Toast.LENGTH_SHORT).show();
            spinner.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mRecipeViewModel.createRecipe(recipeName, recipeContent, Float.parseFloat(recipeRating));
        progressBar.setVisibility(View.GONE);


    }
}

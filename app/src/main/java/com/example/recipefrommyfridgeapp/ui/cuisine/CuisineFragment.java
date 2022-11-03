package com.example.recipefrommyfridgeapp.ui.cuisine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientFragment;
import com.example.recipefrommyfridgeapp.ui.recipe.ChooseRecipeActivity;
import com.example.recipefrommyfridgeapp.viewmodel.CuisineViewModel;

import java.util.List;

public class CuisineFragment extends Fragment implements View.OnClickListener {

    private Button generateButton;
    private TextView cuisine_1, cuisine_2, cuisine_3, cuisine_4, cuisine_5,
            cuisine_6, cuisine_7, cuisine_8, cuisine_9;
    private CuisineViewModel cuisineViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "CuisineFragment.onCreate()");
        cuisineViewModel = new ViewModelProvider(this).get(CuisineViewModel.class);
        cuisineViewModel.retrieveCuisines();

        String s = getArguments().getString(ChooseIngredientFragment.INTENT_INGREDIENT_SELECTED);
        Log.i("test", "onCreate: " + s);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_cuisine, container, false);
        generateButton = v.findViewById(R.id.choose_cuisine_generate);
        generateButton.setOnClickListener(this);
        cuisine_1 = v.findViewById(R.id.cuisine_1);
        cuisine_2 = v.findViewById(R.id.cuisine_2);
        cuisine_3 = v.findViewById(R.id.cuisine_3);
        cuisine_4 = v.findViewById(R.id.cuisine_4);
        cuisine_5 = v.findViewById(R.id.cuisine_5);
        cuisine_6 = v.findViewById(R.id.cuisine_6);
        cuisine_7 = v.findViewById(R.id.cuisine_7);
        cuisine_8 = v.findViewById(R.id.cuisine_8);
        cuisine_9 = v.findViewById(R.id.cuisine_9);

        cuisineViewModel.getCuisineMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Cuisine>>() {
            @Override
            //TODO: find an easier way to display it
            public void onChanged(List<Cuisine> cuisines) {
                if (cuisines != null) {
                    Log.d("checkpoint5", "Successfully get Cuisines list");
                    for (int i = 0; i < cuisines.size(); i++) {
                        Cuisine current = cuisines.get(i);
                        String input = current.getName() + " - " + current.getType();
                        switch (i) {
                            case 0:
                                cuisine_1.setText(input);
                                break;
                            case 1:
                                cuisine_2.setText(input);
                                break;
                            case 2:
                                cuisine_3.setText(input);
                                break;
                            case 3:
                                cuisine_4.setText(input);
                                break;
                            case 4:
                                cuisine_5.setText(input);
                                break;
                            case 5:
                                cuisine_6.setText(input);
                                break;
                            case 6:
                                cuisine_7.setText(input);
                                break;
                            case 7:
                                cuisine_8.setText(input);
                                break;
                            case 8:
                                cuisine_9.setText(input);
                                break;
                        }
                    }
                } else {
                    Log.d("checkpoint5", "Fail to get Cuisine list");
                }


            }
        });


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_cuisine_generate:
                startActivity(new Intent(requireContext(), ChooseRecipeActivity.class));
                break;
        }
    }
}

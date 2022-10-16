package com.example.recipefrommyfridgeapp.ui.chooseingredient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.ui.chooseingredient.IngredientsExpandableListAdapter;

import java.util.HashMap;
import java.util.Map;

public class ChooseIngredientFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_choose_ingredient, container, false);

        //TODO:replace dummy data from data in database
        Map<String, String[]> ingredientMap = new HashMap<>();
        String[] ingredientGroup = new String[]{"Vegetables", "Meat", "Dairy"};

        ingredientMap.put("Vegetables", new String[]{"Celery", "Tomato", "Lettuce", "Cabbage", "Mushroom", "Spinach"});
        ingredientMap.put("Meat", new String[]{"Chicken", "Beef", "Lamb", "Pork"});
        ingredientMap.put("Dairy", new String[]{"Milk", "Yogurt", "Cheese", "Soy Milk"});

        ExpandableListView expandableListView = v.findViewById(R.id.expandable_list_choose_ingredients);
        IngredientsExpandableListAdapter ingredientsExpandableListAdapter = new IngredientsExpandableListAdapter(v.getContext(), ingredientMap, ingredientGroup);
        expandableListView.setAdapter(ingredientsExpandableListAdapter);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.expandable_list_choose_ingredients:
//                break;
//            default:
        }
    }
}

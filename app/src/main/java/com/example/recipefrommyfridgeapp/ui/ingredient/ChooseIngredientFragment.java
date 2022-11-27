package com.example.recipefrommyfridgeapp.ui.ingredient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.ui.cuisine.CuisineActivity;
import com.example.recipefrommyfridgeapp.viewmodel.IngredientViewModel;

import java.util.Arrays;
import java.util.Set;

public class ChooseIngredientFragment extends Fragment implements View.OnClickListener {

    public static final String INTENT_INGREDIENT_SELECTED = "ingredient";
    private IngredientViewModel mIngredientViewModel;
    private IngredientsExpandableListAdapter mIngredientsExpandableListAdapter;
    private Set<String> mIngredientSelected;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIngredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        mIngredientViewModel.retrieveIngredient();
        mIngredientSelected = mIngredientViewModel.getIngredientSelectedMutableLiveData().getValue();
        Log.i("rotation","ingredient fragment onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_choose_ingredient, container, false);

        final ExpandableListView expandableListView = v.findViewById(R.id.expandable_list_choose_ingredients);
        mIngredientsExpandableListAdapter = new IngredientsExpandableListAdapter(v.getContext(), mIngredientSelected);
        expandableListView.setAdapter(mIngredientsExpandableListAdapter);

        //update data in the Adapter
        mIngredientViewModel.getIngredientMutableLiveData().observe(getViewLifecycleOwner(),
                map -> mIngredientsExpandableListAdapter.updateItems(map));
        mIngredientViewModel.getIngredientGroupMutableLiveData().observe(getViewLifecycleOwner(),
                list -> mIngredientsExpandableListAdapter.updateItems(list));

        final Button chooseCuisineButton = v.findViewById(R.id.button_choose_cuisine);
        chooseCuisineButton.setOnClickListener(this);

        Log.i("rotation","ingredient fragment onCreateView");

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_choose_cuisine) {
            Intent intent = new Intent(requireContext(), CuisineActivity.class);
            intent.putExtra(INTENT_INGREDIENT_SELECTED, mIngredientSelected.toArray(new String[0]));
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String ing[] = new String[mIngredientSelected.size()];
        ing = mIngredientSelected.toArray(ing);
        outState.putStringArray("ingredients", ing);
        Log.i("rotation", "Saving " + Arrays.toString(ing));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            String checked[] = (String[]) savedInstanceState.get("ingredients");
            String combine = "";
            for(String item : checked){
                combine += item + " ";
                mIngredientSelected.add(item);
//                Log.i("rotation", "put back " + item);
            }
            Log.i("rotation","retrieving selected ingredients");
            Toast.makeText(getActivity(), "You selected " + combine, Toast.LENGTH_LONG).show();
        }
    }
}

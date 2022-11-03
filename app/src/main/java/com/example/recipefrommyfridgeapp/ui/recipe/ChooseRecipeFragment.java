package com.example.recipefrommyfridgeapp.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientActivity;
import com.example.recipefrommyfridgeapp.viewmodel.CuisineViewModel;
import com.example.recipefrommyfridgeapp.viewmodel.RecipeViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseRecipeFragment extends Fragment {

    private RecyclerView mRecipeRecyclerView;
    private List<Recipe> mRecipeList;
    private RecipeListAdapter mRecipeListAdapter;

    private RecipeViewModel mRecipeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "ChooseRecipeFragment.onCreate()");
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        Log.d("checkpoint5", "ChooseRecipeFragment.onCreateView");

        mRecipeRecyclerView = (RecyclerView) view
                .findViewById(R.id.recipe_recycler_view);
        mRecipeRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecipeList = new ArrayList<>();
        mRecipeViewModel.retrieveRecipeList();
        mRecipeViewModel.getRecipeListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                mRecipeList.clear();
                for (Recipe current: recipes){
                    mRecipeList.add(current);
                }
                Log.d("checkpoint5", Integer.toString(mRecipeList.size()));
                mRecipeListAdapter = new RecipeListAdapter(mRecipeList);
                mRecipeRecyclerView.setAdapter(mRecipeListAdapter);
            }
        });
        return view;
    }

    private class RecipeListHolder extends RecyclerView.ViewHolder{

        private Recipe mRecipe;
        private TextView mNameTextView, mContentTextView, mRatingTextView, CuisineIdTextView;
        private String recipeId;

        public RecipeListHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            mNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            mContentTextView = (TextView) itemView.findViewById(R.id.recipe_content);
            mRatingTextView = (TextView) itemView.findViewById(R.id.recipe_rating);
            CuisineIdTextView = (TextView) itemView.findViewById(R.id.recipe_cuisineId);
        }

        public void bind(Recipe current) {
            mRecipe = current;
            mNameTextView.setText(mRecipe.getName());
            mContentTextView.setText(mRecipe.getContent());
            mRatingTextView.setText(Float.toString(mRecipe.getRating()));
            CuisineIdTextView.setText(mRecipe.getCuisineId());
            mRecipeViewModel.returnRecipeId(mRecipe.getName());
            mRecipeViewModel.getRecipeIdMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s != null){
                        recipeId = "" + s;
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new RecipeFragment(recipeId);
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .setReorderingAllowed(true)
                            .addToBackStack("Get recipe details")
                            .commit();
                }
            });
        }
    }

    private class RecipeListAdapter extends RecyclerView.Adapter<RecipeListHolder> {

        private List<Recipe> mRecipes;
        public RecipeListAdapter(List<Recipe> recipes) {
            mRecipes = recipes;
        }
        @NonNull
        @Override
        public RecipeListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RecipeListHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeListHolder holder, int position) {
            Recipe current = mRecipes.get(position);
            holder.bind(current);
        }

        @Override
        public int getItemCount() {
            return mRecipes.size();
        }
    }
}

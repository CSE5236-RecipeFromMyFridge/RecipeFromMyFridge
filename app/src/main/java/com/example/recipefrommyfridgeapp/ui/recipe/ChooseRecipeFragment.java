package com.example.recipefrommyfridgeapp.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.RecipeLab;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ChooseRecipeFragment extends Fragment {

    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mRecipeRecyclerView = (RecyclerView) view
                .findViewById(R.id.recipe_recycler_view);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Recipe> options =
                new FirebaseRecyclerOptions.Builder<Recipe>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Recipes"), Recipe.class)
                        .build();
        mAdapter = new RecipeAdapter(options);
        mRecipeRecyclerView.setAdapter(mAdapter);

//        updateUI();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        mAdapter.stopListening();
    }



//    private void updateUI() {
//        RecipeLab recipeLab = RecipeLab.get(getActivity());
//        List<Recipe> recipes = recipeLab.getRecipes();
//
//        mAdapter = new RecipeAdapter(recipes);
//        mRecipeRecyclerView.setAdapter(mAdapter);
//    }
//
//    private class RecipeHolder extends RecyclerView.ViewHolder
//            implements View.OnClickListener{
//
//        private Recipe mRecipe;
//        private TextView mNameTextView;
//        private TextView mContentTextView;
//        private TextView mRatingTextView;
//
//        public RecipeHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
//            itemView.setOnClickListener(this);
//            mNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
//            mContentTextView = (TextView) itemView.findViewById(R.id.recipe_content);
//            mRatingTextView = (TextView) itemView.findViewById(R.id.recipe_rating);
//        }
//
//        public void bind(Recipe recipe) {
//            mRecipe = recipe;
//            mNameTextView.setText(mRecipe.getName());
//            mContentTextView.setText(mRecipe.getContent());
//            mRatingTextView.setText(Float.toString(mRecipe.getRating()));
//        }
//
//        @Override
//        public void onClick(View view) {
//            Toast.makeText(getActivity(),
//                            mRecipe.getName() + " clicked!", Toast.LENGTH_SHORT)
//                    .show();
//        }
//    }
//
//    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
//        private List<Recipe> mRecipes;
//        public RecipeAdapter(List<Recipe> recipes) {
//            mRecipes = recipes;
//        }
//        @NonNull
//        @Override
//        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            return new RecipeHolder(layoutInflater, parent);
//        }
//        @Override
//        public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
//            Recipe recipe = mRecipes.get(position);
//            holder.bind(recipe);
//        }
//        @Override
//        public int getItemCount() {
//            return mRecipes.size();
//        }
//    }

}

package com.example.recipefrommyfridgeapp.ui.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecipeAdapter extends FirebaseRecyclerAdapter<Recipe, RecipeAdapter.RecipeViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecipeAdapter(@NonNull FirebaseRecyclerOptions<Recipe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecipeViewHolder holder, int position, @NonNull Recipe model) {
        holder.mNameTextView.setText(model.getName());
        holder.mContentTextView.setText(model.getContent());
        holder.mRatingTextView.setText(Float.toString(model.getRating()));
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{
        private Recipe mRecipe;
        private TextView mNameTextView, mContentTextView, mRatingTextView;
        public RecipeViewHolder(@NonNull View recipeView){
            super(recipeView);
            mNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            mContentTextView = (TextView) itemView.findViewById(R.id.recipe_content);
            mRatingTextView = (TextView) itemView.findViewById(R.id.recipe_rating);
        }
    }
}

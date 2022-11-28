package com.example.recipefrommyfridgeapp.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.viewmodel.SavedRecipeViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

public class SavedRecipeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SavedRecipeViewModel mSavedRecipeViewModel;
    private FirebaseRecyclerOptions<Recipe> options;
    private RecipeAdapter mAdapter;
    private String userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "SavedRecipeFragment.onCreate()");
        mSavedRecipeViewModel = new ViewModelProvider(this).get(SavedRecipeViewModel.class);
        userId = (String) getActivity().getIntent()
                .getSerializableExtra(SavedRecipeActivity.EXTRA_USER_ID);
        Log.d("checkpoint5", "SavedRecipeFragment: " + userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        Log.d("checkpoint5", "SavedRecipeFragment.onCreateView");

        mRecyclerView = (RecyclerView) view
                .findViewById(R.id.recipe_recycler_view);
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        options = mSavedRecipeViewModel.retrieveSavedRecipes(userId);
        mAdapter = new RecipeAdapter(options);
        mRecyclerView.setAdapter(mAdapter);

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

    private class RecipeAdapter extends FirebaseRecyclerAdapter<Recipe, RecipeAdapter.RecipeViewHolder> {
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
        protected void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position, @NonNull Recipe model) {
            Log.d("checkpoint5", "onBindViewHolder");
            String key = model.getRecipeId();
            holder.mNameTextView.setText(model.getName());
            holder.mContentTextView.setText(model.getContent());
            holder.mRatingTextView.setText(Float.toString(model.getRating()));
            holder.CuisineIdTextView.setText(model.getCuisineId());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = SavedRecipeDetailsFragment.newInstance(key);
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .setReorderingAllowed(true)
                            .addToBackStack("Get recipe details")
                            .commit();
                }
            });

            holder.deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSavedRecipeViewModel.deleteSavedRecipe(userId, key);
                }
            });

        }

        @NonNull
        @Override
        public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_saved_recipe, parent, false);
            return new RecipeAdapter.RecipeViewHolder(view);
        }

        private class RecipeViewHolder extends RecyclerView.ViewHolder {
            private TextView mNameTextView, mContentTextView, mRatingTextView, CuisineIdTextView;
            private Button deleteRecipeButton;

            public RecipeViewHolder(@NonNull View recipeView) {
                super(recipeView);
                mNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
                mContentTextView = (TextView) itemView.findViewById(R.id.recipe_content);
                mRatingTextView = (TextView) itemView.findViewById(R.id.recipe_rating);
                CuisineIdTextView = (TextView) itemView.findViewById(R.id.recipe_cuisineId);
                deleteRecipeButton = (Button) itemView.findViewById(R.id.recipe_item_delete_button);
             }
        }
    }


}

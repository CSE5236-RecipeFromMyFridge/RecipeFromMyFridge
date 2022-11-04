package com.example.recipefrommyfridgeapp.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.ui.cuisine.CuisineFragment;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientFragment;
import com.example.recipefrommyfridgeapp.viewmodel.LoggedInViewModel;
import com.example.recipefrommyfridgeapp.viewmodel.RecipeViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;

public class ChooseRecipeFragment extends Fragment {

    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;
    private FirebaseRecyclerOptions<Recipe> options;

    private RecipeViewModel mRecipeViewModel;
    private LoggedInViewModel mLoggedInViewModel;
    private String userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "ChooseRecipeFragment.onCreate()");
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mLoggedInViewModel = new ViewModelProvider(this).get(LoggedInViewModel.class);
        mLoggedInViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    userId = "" + firebaseUser.getUid();
                    Log.d("checkpoint5", "ChooseRecipeFragment.onCreate" + userId);
                }
            }
        });
        String cuisine = getArguments().getString(CuisineFragment.INTENT_CUISINE_SELECTED);
        String ingredient = getArguments().getString(ChooseIngredientFragment.INTENT_INGREDIENT_SELECTED);
        options = mRecipeViewModel.retrieveRecipes(cuisine, "Beef");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        Log.d("checkpoint5", "ChooseRecipeFragment.onCreateView");

        mRecipeRecyclerView = (RecyclerView) view
                .findViewById(R.id.recipe_recycler_view);
        mRecipeRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new RecipeAdapter(options);
        mRecipeRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
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
            final String key = getRef(position).getKey();

            Log.d("checkpoint5", "onBindViewHolder");
            holder.mNameTextView.setText(model.getName());
            holder.mContentTextView.setText(model.getContent());
            holder.mRatingTextView.setText(Float.toString(model.getRating()));
            holder.CuisineIdTextView.setText(model.getCuisineId());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("checkpoint5", "ChooseRecipeFragment.onBindView: " + userId);
                    Fragment fragment = new RecipeFragment(userId, key);
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .setReorderingAllowed(true)
                            .addToBackStack("Get recipe details")
                            .commit();
                }
            });
        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
            return new RecipeViewHolder(view);
        }

        private class RecipeViewHolder extends RecyclerView.ViewHolder {
            private final TextView mNameTextView;
            private final TextView mContentTextView;
            private final TextView mRatingTextView;
            private final TextView CuisineIdTextView;

            public RecipeViewHolder(@NonNull View recipeView) {
                super(recipeView);
                mNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
                mContentTextView = (TextView) itemView.findViewById(R.id.recipe_content);
                mRatingTextView = (TextView) itemView.findViewById(R.id.recipe_rating);
                CuisineIdTextView = (TextView) itemView.findViewById(R.id.recipe_cuisineId);
            }
        }
    }
}
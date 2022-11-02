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
    private CuisineViewModel mCuisineViewModel;

    private List<String> names;
    private String cuisineIdChosen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "ChooseRecipeFragment.onCreate()");
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mCuisineViewModel = new ViewModelProvider(this).get(CuisineViewModel.class);
        mCuisineViewModel.retrieveCuisines();
        names = new ArrayList<>();
        mCuisineViewModel.getCuisineMutableLiveData().observe(this, new Observer<List<Cuisine>>() {
            @Override
            public void onChanged(List<Cuisine> cuisines) {
                for (int i = 0; i < cuisines.size(); i++){
                    names.add(cuisines.get(i).getType());
                }
            }
        });
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
        private Button editRecipeButton, deleteRecipeButton;
        private String recipeId;

        public RecipeListHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            mNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            mContentTextView = (TextView) itemView.findViewById(R.id.recipe_content);
            mRatingTextView = (TextView) itemView.findViewById(R.id.recipe_rating);
            CuisineIdTextView = (TextView) itemView.findViewById(R.id.recipe_cuisineId);
            editRecipeButton = (Button) itemView.findViewById(R.id.recipe_item_edit_button);
            deleteRecipeButton = (Button) itemView.findViewById(R.id.recipe_item_delete_button);
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
                    Intent intent = RecipeActivity.newIntent(getActivity(), recipeId);
                    startActivity(intent);
                }
            });
            editRecipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(mNameTextView.getContext())
                            .setContentHolder(new ViewHolder(R.layout.update_popup))
                            .setExpanded(true, 1400)
                            .create();

                    View view = dialogPlus.getHolderView();
                    EditText name = view.findViewById(R.id.edit_recipe_name);
                    EditText content = view.findViewById(R.id.edit_recipe_content);
                    EditText rating = view.findViewById(R.id.edit_recipe_rating);
                    Spinner spinner = view.findViewById(R.id.update_recipe_spinner);
                    Button update = view.findViewById(R.id.update_recipe_button);
                    name.setText(mRecipe.getName());
                    content.setText(mRecipe.getContent());
                    rating.setText(Float.toString(mRecipe.getRating()));
                    ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, names);
                    mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinner.setAdapter(mArrayAdapter);
                    Log.d("checkpoint5", "update cuisine list retrieved");
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

                    dialogPlus.show();

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("name", name.getText().toString());
                            map.put("content", content.getText().toString());
                            map.put("rating", Float.parseFloat(rating.getText().toString()));
                            map.put("cuisineId", cuisineIdChosen);
                            mRecipeViewModel.updateRecipe(recipeId, map);
                            dialogPlus.dismiss();
                        }
                    });
                }
            });

            deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecipeViewModel.deleteRecipe(recipeId);
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

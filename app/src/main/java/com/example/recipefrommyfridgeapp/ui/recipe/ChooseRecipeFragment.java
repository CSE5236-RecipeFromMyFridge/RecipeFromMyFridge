package com.example.recipefrommyfridgeapp.ui.recipe;

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
    private RecipeAdapter mAdapter;
    private FirebaseRecyclerOptions<Recipe> options;

    private RecipeViewModel mRecipeViewModel;
    private CuisineViewModel mCuisineViewModel;

    private List<String> names;
    private String cuisineIdChosen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "ChooseRecipeFragment.onCreate()");
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        options = mRecipeViewModel.retrieveRecipes();
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
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
            final DatabaseReference itemRef = getRef(position);
            final String key = itemRef.getKey();
            holder.mNameTextView.setText(model.getName());
            holder.mContentTextView.setText(model.getContent());
            holder.mRatingTextView.setText(Float.toString(model.getRating()));
            holder.CuisineIdTextView.setText(model.getCuisineId());

            holder.deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecipeViewModel.deleteRecipe(key);
                }
            });

            holder.editRecipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(holder.mNameTextView.getContext())
                            .setContentHolder(new ViewHolder(R.layout.update_popup))
                            .setExpanded(true, 1400)
                            .create();

                    View view = dialogPlus.getHolderView();
                    EditText name = view.findViewById(R.id.edit_recipe_name);
                    EditText content = view.findViewById(R.id.edit_recipe_content);
                    EditText rating = view.findViewById(R.id.edit_recipe_rating);
                    Spinner spinner = view.findViewById(R.id.update_recipe_spinner);
                    Button update = view.findViewById(R.id.update_recipe_button);
                    name.setText(model.getName());
                    content.setText(model.getContent());
                    rating.setText(Float.toString(model.getRating()));
                    ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, names);
                    mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinner.setAdapter(mArrayAdapter);
                    Log.d("checkpoint5", "update cuisine list retrieved");
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            cuisineIdChosen = names.get(position);
                            Log.d("checkpoint5", "update cuisine id retrieved");
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
                            mRecipeViewModel.updateRecipe(key, map);
                            dialogPlus.dismiss();
                        }
                    });
                }
            });


        }

        @NonNull
        @Override
        public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
            return new RecipeViewHolder(view);
        }

        private class RecipeViewHolder extends RecyclerView.ViewHolder{
            private TextView mNameTextView, mContentTextView, mRatingTextView, CuisineIdTextView;
            private Button editRecipeButton, deleteRecipeButton;
            public RecipeViewHolder(@NonNull View recipeView){
                super(recipeView);
                mNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
                mContentTextView = (TextView) itemView.findViewById(R.id.recipe_content);
                mRatingTextView = (TextView) itemView.findViewById(R.id.recipe_rating);
                CuisineIdTextView = (TextView) itemView.findViewById(R.id.recipe_cuisineId);
                editRecipeButton = (Button) itemView.findViewById(R.id.recipe_item_edit_button);
                deleteRecipeButton = (Button) itemView.findViewById(R.id.recipe_item_delete_button);
            }
        }
    }

}

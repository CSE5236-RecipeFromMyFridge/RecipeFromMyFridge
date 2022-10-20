//package com.example.recipefrommyfridgeapp.ui.recipe;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.recipefrommyfridgeapp.R;
//import com.example.recipefrommyfridgeapp.model.Recipe;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.orhanobut.dialogplus.DialogPlus;
//import com.orhanobut.dialogplus.ViewHolder;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class RecipeAdapter extends FirebaseRecyclerAdapter<Recipe, RecipeAdapter.RecipeViewHolder> {
//    /**
//     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
//     * {@link FirebaseRecyclerOptions} for configuration options.
//     *
//     * @param options
//     */
//    public RecipeAdapter(@NonNull FirebaseRecyclerOptions<Recipe> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull RecipeViewHolder holder, int position, @NonNull Recipe model) {
//        final DatabaseReference itemRef = getRef(position);
//        final String key = itemRef.getKey();
//        holder.mNameTextView.setText(model.getName());
//        holder.mContentTextView.setText(model.getContent());
//        holder.mRatingTextView.setText(Float.toString(model.getRating()));
//
//        holder.deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseDatabase.getInstance().getReference().child("Recipes")
//                        .child(getRef(position).getKey()).removeValue();
//            }
//        });
//
//        holder.editRecipeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.mNameTextView.getContext())
//                        .setContentHolder(new ViewHolder(R.layout.update_popup))
//                        .setExpanded(true, 1200)
//                        .create();
//
//                View view = dialogPlus.getHolderView();
//                EditText name = view.findViewById(R.id.edit_recipe_name);
//                EditText content = view.findViewById(R.id.edit_recipe_content);
//                EditText rating = view.findViewById(R.id.edit_recipe_rating);
//                Button update = view.findViewById(R.id.update_recipe_button);
//                name.setText(model.getName());
//                content.setText(model.getContent());
//                rating.setText(Float.toString(model.getRating()));
//
//                dialogPlus.show();
//
//                update.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("name", name.getText().toString());
//                        map.put("content", content.getText().toString());
//                        map.put("rating", Float.parseFloat(rating.getText().toString()));
//
//                        FirebaseDatabase.getInstance().getReference().child("Recipes")
//                                .child(getRef(position).getKey()).updateChildren(map)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()){
//                                            Toast.makeText(holder.mNameTextView.getContext(), "Recipe Updated Successfully!", Toast.LENGTH_SHORT).show();
//                                            dialogPlus.dismiss();
//                                            return;
//                                        } else {
//                                            Toast.makeText(holder.mNameTextView.getContext(), "Failed to login!", Toast.LENGTH_SHORT).show();
//                                            dialogPlus.dismiss();
//                                            return;
//                                        }
//                                    }
//                                });
//                    }
//                });
//
//
//            }
//        });
//
//
//    }
//
//    @NonNull
//    @Override
//    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
//        return new RecipeViewHolder(view);
//    }
//
//    class RecipeViewHolder extends RecyclerView.ViewHolder{
//        private Recipe mRecipe;
//        private TextView mNameTextView, mContentTextView, mRatingTextView;
//        private Button editRecipeButton, deleteRecipeButton;
//        public RecipeViewHolder(@NonNull View recipeView){
//            super(recipeView);
//            mNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
//            mContentTextView = (TextView) itemView.findViewById(R.id.recipe_content);
//            mRatingTextView = (TextView) itemView.findViewById(R.id.recipe_rating);
//            editRecipeButton = (Button) itemView.findViewById(R.id.recipe_item_edit_button);
//            deleteRecipeButton = (Button) itemView.findViewById(R.id.recipe_item_delete_button);
//        }
//    }
//}

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

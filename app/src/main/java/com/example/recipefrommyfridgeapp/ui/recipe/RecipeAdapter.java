package com.example.recipefrommyfridgeapp.ui.recipe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

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
    protected void onBindViewHolder(@NonNull RecipeViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Recipe model) {
        holder.mNameTextView.setText(model.getName());
        holder.mContentTextView.setText(model.getContent());
        holder.mRatingTextView.setText(Float.toString(model.getRating()));

        holder.deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.mNameTextView.getContext());
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Recipes")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.mNameTextView.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });

        holder.editRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.mNameTextView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.edit_recipe_name);
                EditText content = view.findViewById(R.id.edit_recipe_content);
                EditText rating = view.findViewById(R.id.edit_recipe_rating);
                Button update = view.findViewById(R.id.update_recipe_button);
                name.setText(model.getName());
                content.setText(model.getContent());
                rating.setText(Float.toString(model.getRating()));

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("content", content.getText().toString());
                        map.put("rating", Float.parseFloat(rating.getText().toString()));

                        FirebaseDatabase.getInstance().getReference().child("Recipes")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(holder.mNameTextView.getContext(), "Recipe Updated Successfully!", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                            return;
                                        } else {
                                            Toast.makeText(holder.mNameTextView.getContext(), "Failed to login!", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                            return;
                                        }
                                    }
                                });
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

    class RecipeViewHolder extends RecyclerView.ViewHolder{
        private Recipe mRecipe;
        private TextView mNameTextView, mContentTextView, mRatingTextView;
        private Button editRecipeButton, deleteRecipeButton;
        public RecipeViewHolder(@NonNull View recipeView){
            super(recipeView);
            mNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            mContentTextView = (TextView) itemView.findViewById(R.id.recipe_content);
            mRatingTextView = (TextView) itemView.findViewById(R.id.recipe_rating);
            editRecipeButton = (Button) itemView.findViewById(R.id.recipe_item_edit_button);
            deleteRecipeButton = (Button) itemView.findViewById(R.id.recipe_item_delete_button);
        }
    }
}

package com.example.recipefrommyfridgeapp.repository;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.model.Ingredient;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppRepository {

    private final Application application;
    private final MutableLiveData<FirebaseUser> mUserMutableLiveData;
    private final MutableLiveData<Boolean> loggedOutMutableLiveData;
    private final FirebaseAuth auth;
    private final FirebaseDatabase db;
    private final MutableLiveData<List<Cuisine>> cuisineMutableLiveData;
    private final MutableLiveData<Map<String, List<Ingredient>>> mIngredientMutableLiveData;
    private final MutableLiveData<List<String>> mIngredientGroupMutableLiveData;
    private final MutableLiveData<Recipe> mRecipeMutableLiveData;
    private final MutableLiveData<List<String>> mRecipeListMutableLiveData;
    private final MutableLiveData<Recipe> savedRecipeMutableLiveData;
    private final MutableLiveData<List<String>> savedRecipeListMutableLiveData;
    private final MutableLiveData<List<String>> userSavedRecipeMutableLiveData;


    public AppRepository(Application application) {
        this.application = application;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        mUserMutableLiveData = new MutableLiveData<>();
        loggedOutMutableLiveData = new MutableLiveData<>();
        cuisineMutableLiveData = new MutableLiveData<>();
        mIngredientMutableLiveData = new MutableLiveData<>();
        mIngredientGroupMutableLiveData = new MutableLiveData<>();
        mRecipeMutableLiveData = new MutableLiveData<>();
        mRecipeListMutableLiveData = new MutableLiveData<>();
        savedRecipeMutableLiveData = new MutableLiveData<>();
        savedRecipeListMutableLiveData = new MutableLiveData<>();
        userSavedRecipeMutableLiveData = new MutableLiveData<>();

        if (auth.getCurrentUser() != null) {
            getUserMutableLiveData().postValue(auth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
        } else {
            loggedOutMutableLiveData.postValue(true);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(name, email, password);
                            db.getReference("Users")
                                    .child(auth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(application.getApplicationContext(), "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(application.getApplicationContext(), "(DB) Failed to register!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            //mUserMutableLiveData.postValue(auth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "(Auth) Failed to register!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mUserMutableLiveData.postValue(auth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "(Auth) Failed to login!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOut(String userId) {
        DatabaseReference previous = db.getReference(userId);
        if (previous != null) {
            previous.removeValue();
        }
        auth.signOut();
        loggedOutMutableLiveData.postValue(true);
    }

    public void retrieveCuisines() {
        List<Cuisine> cuisines = new ArrayList<>();
        DatabaseReference ref = db.getReference("Cuisines");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()) {
                    Cuisine single = post.getValue(Cuisine.class);
                    cuisines.add(single);
                }
                cuisineMutableLiveData.postValue(cuisines);
                Log.d("checkpoint5", "Successfully retrieve Cuisines");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("checkpoint5", "Fail to retrieve Cuisines");
            }
        });
    }

    public void retrieveRecipeIdList() {
        List<String> recipes = new ArrayList<>();
        DatabaseReference ref = db.getReference("Recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()) {
                    String single = post.getKey();
                    recipes.add(single);
                }
                mRecipeListMutableLiveData.postValue(recipes);
                Log.d("checkpoint5", "Successfully retrieve recipes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("checkpoint5", "Fail to retrieve recipes");
            }
        });
    }

    public void retrieveIngredients() {
        Map<String, List<Ingredient>> ingredients = new HashMap<>();
        List<String> ingredientGroup = new ArrayList<>();
        DatabaseReference ref = db.getReference("Ingredients");
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    for (DataSnapshot post : task.getResult().getChildren()) {
                        String ingredientType = post.getKey();
                        ingredientGroup.add(ingredientType);
                        if (!ingredients.containsKey(ingredientType))
                            ingredients.put(ingredientType, new ArrayList<>());

                        //get all ingredients under the category (eg. Vegetables, Meat, etc)
                        for (DataSnapshot i : post.getChildren()) {
                            Ingredient ingredient = i.getValue(Ingredient.class);
                            List<Ingredient> tmp = ingredients.remove(ingredientType);
                            tmp.add(ingredient);
                            ingredients.put(ingredientType, tmp);
                        }
                    }
                    mIngredientMutableLiveData.postValue(ingredients);
                    mIngredientGroupMutableLiveData.postValue(ingredientGroup);
                }
            }
        });
    }

    public void resetPassword(String name, String email, String password, String newPassword) {
        FirebaseUser user = auth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("checkpoint5", "User re-authenticated");
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Fail to reauthenticate!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(application.getApplicationContext(), "Successfully reset the password!", Toast.LENGTH_SHORT).show();
                            Log.d("checkpoint5", "User reset the password");
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Fail to update the password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        User person = new User(name, email, newPassword);
        db.getReference("Users").child(user.getUid()).setValue(person);
    }

    public FirebaseRecyclerOptions<Recipe> retrieveRecipes() {
        DatabaseReference ref = db.getReference("Recipes");
        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(ref, Recipe.class)
                .build();
        return options;
    }

    public FirebaseRecyclerOptions<Recipe> retrieveRecipes(String cuisine, String ingredient) {
        DatabaseReference ref = db.getReference("Recipes");
        DatabaseReference recipeQuery = db.getReference("RecipeQuery");
        DatabaseReference newQuery = recipeQuery.push();

        String[] cuisines = cuisine.split(","), ingredients = ingredient.split(",");

        for (String c : cuisines) {
            Query q = ref.orderByChild("cuisineId").equalTo(c); //get by each cuisine
            q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        Recipe r = s.getValue(Recipe.class);
                        if (r.hasIngredient(ingredient)) {
                            newQuery.push().setValue(r);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //TODO: find out a way to delete off the query from databse. maybe group under userid instead?
        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(newQuery, Recipe.class)
                .build();
        return options;
    }

    public void getCurrentRecipe(String recipeId) {
        DatabaseReference ref = db.getReference("Recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    if (shot.getKey().equals(recipeId)) {
                        Recipe current = shot.getValue(Recipe.class);
                        mRecipeMutableLiveData.postValue(current);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public FirebaseRecyclerOptions<Recipe> retrieveSavedRecipes(String userId) {
        DatabaseReference ref = db.getReference("SavedRecipes").child(userId);
        List<String> recipeIds = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String current = shot.getKey();
                    recipeIds.add(current);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference previous = db.getReference(userId);
        if (previous != null) {
            previous.removeValue();
        }

        DatabaseReference currentUserSavedRecipe = db.getReference(userId);
        DatabaseReference recipes = db.getReference("Recipes");
        recipes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    String currentId = shot.getKey();
                    if (recipeIds.contains(currentId)) {
                        Recipe include = shot.getValue(Recipe.class);
                        currentUserSavedRecipe.child(currentId).setValue(include);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(currentUserSavedRecipe, Recipe.class)
                .build();
        return options;
    }

    public void getCurrentSavedRecipe(String userId, String recipeId) {
        DatabaseReference ref = db.getReference(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    if (shot.getKey().equals(recipeId)) {
                        Recipe current = shot.getValue(Recipe.class);
                        savedRecipeMutableLiveData.postValue(current);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void retrieveSavedRecipeIdList(String userId) {
        List<String> recipes = new ArrayList<>();
        DatabaseReference ref = db.getReference(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()) {
                    String single = post.getKey();
                    recipes.add(single);
                }
                savedRecipeListMutableLiveData.postValue(recipes);
                Log.d("checkpoint5", "Successfully retrieve saved recipes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("checkpoint5", "Fail to retrieve saved recipes");
            }
        });
    }

    public void userSavedRecipe(String userId) {
        List<String> recipes = new ArrayList<>();
        DatabaseReference ref = db.getReference("SavedRecipes").child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()) {
                    String single = post.getKey();
                    recipes.add(single);
                }
                userSavedRecipeMutableLiveData.postValue(recipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void saveRecipe(String userId, String recipeId) {
        DatabaseReference ref = db.getReference("SavedRecipes").child(userId);
        ref.child(recipeId).setValue(true);
    }

    public void deleteSavedRecipe(String userId, String recipeId) {
        DatabaseReference ref = db.getReference("SavedRecipes");
        ref.child(userId).child(recipeId).removeValue();
        DatabaseReference currentUserSaved = db.getReference(userId);
        currentUserSaved.child(recipeId).removeValue();
    }


    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    public MutableLiveData<List<Cuisine>> getCuisineMutableLiveData() {
        return cuisineMutableLiveData;
    }

    public MutableLiveData<Map<String, List<Ingredient>>> getIngredientsMutableLiveData() {
        return mIngredientMutableLiveData;
    }

    public MutableLiveData<List<String>> getIngredientsGroupMutableLiveData() {
        return mIngredientGroupMutableLiveData;
    }

    public MutableLiveData<Recipe> getRecipeMutableLiveData() {
        return mRecipeMutableLiveData;
    }

    public MutableLiveData<List<String>> getRecipeListMutableLiveData() {
        return mRecipeListMutableLiveData;
    }

    public MutableLiveData<Recipe> getSavedRecipeMutableLiveData() {
        return savedRecipeMutableLiveData;
    }

    public MutableLiveData<List<String>> getSavedRecipeListMutableLiveData() {
        return savedRecipeListMutableLiveData;
    }

    public MutableLiveData<List<String>> getUserSavedRecipeMutableLiveData() {
        return userSavedRecipeMutableLiveData;
    }
}
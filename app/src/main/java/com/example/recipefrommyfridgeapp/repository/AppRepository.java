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
    private final MutableLiveData<List<Recipe>> recipeListMutableLiveData;
    private final MutableLiveData<String> recipeIdMutableLiveData;


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
        recipeListMutableLiveData = new MutableLiveData<>();
        recipeIdMutableLiveData = new MutableLiveData<>();

        if (auth.getCurrentUser() != null) {
            getUserMutableLiveData().postValue(auth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
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

    public void logOut() {
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

    public void retrieveRecipeList() {
        List<Recipe> recipes = new ArrayList<>();
        DatabaseReference ref = db.getReference("Recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()) {
                    Recipe single = post.getValue(Recipe.class);
                    recipes.add(single);
                }
                recipeListMutableLiveData.postValue(recipes);
                Log.d("checkpoint5", "Successfully retrieve Recipes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("checkpoint5", "Fail to retrieve Recipes");
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


//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot post : snapshot.getChildren()) {
//                    String ingredientType = post.getKey();
//                    ingredientGroup.add(ingredientType);
//                    if (!ingredients.containsKey(ingredientType))
//                        ingredients.put(ingredientType, new ArrayList<>());
//
//                    //get all ingredients under the category (eg. Vegetables, Meat, etc)
//                    for (DataSnapshot i : post.getChildren()) {
//                        Ingredient ingredient = i.getValue(Ingredient.class);
//                        List<Ingredient> tmp = ingredients.remove(ingredientType);
//                        tmp.add(ingredient);
//                        ingredients.put(ingredientType, tmp);
//                    }
//                }
//                mIngredientMutableLiveData.postValue(ingredients);
//                mIngredientGroupMutableLiveData.postValue(ingredientGroup);
//                Log.i("test", "onDataChange: " + mIngredientGroupMutableLiveData.getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
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

//    public void createRecipe(String cuisineId, String name, String content, Float rating) {
//        Recipe recipe = new Recipe(cuisineId, name, content, rating);
//        DatabaseReference ref = db.getReference("Recipes");
//        String id = ref.push().getKey();
//        ref.child(id).setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(application.getApplicationContext(), "Recipe has been added successfully!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(application.getApplicationContext(), "(DB) Failed to add the recipe!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

//    public void deleteRecipe(String id) {
//        DatabaseReference ref = db.getReference("Recipes");
//        ref.child(id).removeValue();
//    }

//    public void updateRecipe(String id, Map<String, Object> newRecipe) {
//        DatabaseReference ref = db.getReference("Recipes");
//        ref.child(id).updateChildren(newRecipe)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(application.getApplicationContext(), "Recipe Updated Successfully!", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(application.getApplicationContext(), "Failed to login!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

//    public FirebaseRecyclerOptions<Recipe> retrieveRecipes() {
//        DatabaseReference ref = db.getReference("Recipes");
//        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
//                .setQuery(ref, Recipe.class)
//                .build();
//        return options;
//    }

    public void getCurrentRecipe(String recipeId){
        DatabaseReference ref = db.getReference("Recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()){
                    if (shot.getKey().equals(recipeId)){
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

    public void returnRecipeId(String recipeName){
        DatabaseReference ref = db.getReference("Recipes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot shot : snapshot.getChildren()){
                    Recipe current = shot.getValue(Recipe.class);
                    if (current.getName().equals(recipeName)){
                        recipeIdMutableLiveData.postValue(shot.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    public MutableLiveData<List<Recipe>> getRecipeListMutableLiveData() {
        return recipeListMutableLiveData;
    }

    public MutableLiveData<String> getRecipeIdMutableLiveData() {
        return recipeIdMutableLiveData;
    }
}

package com.example.recipefrommyfridgeapp.repository;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.model.Recipe;
import com.example.recipefrommyfridgeapp.model.User;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
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

    private Application application;
    private MutableLiveData<FirebaseUser> mUserMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private MutableLiveData<List<Cuisine>> cuisineMutableLiveData;

    public AppRepository(Application application){
        this.application = application;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        mUserMutableLiveData = new MutableLiveData<>();
        loggedOutMutableLiveData = new MutableLiveData<>();
        cuisineMutableLiveData = new MutableLiveData<>();

        if (auth.getCurrentUser() != null){
            getUserMutableLiveData().postValue(auth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String name, String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(name, email, password);
                            db.getReference("Users")
                                    .child(auth.getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
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
    public void login(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mUserMutableLiveData.postValue(auth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "(Auth) Failed to login!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOut(){
        auth.signOut();
        loggedOutMutableLiveData.postValue(true);
    }

    public void retrieveCuisines(){
        List<Cuisine> cuisines = new ArrayList<>();
        DatabaseReference ref = db.getReference("Cuisines");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot post : snapshot.getChildren()){
                    Cuisine single = post.getValue(Cuisine.class);
                    String str = single.getName() + " - " + single.getType();
                    Log.d("checkpoint5", str);
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

        Log.d("checkpoint5", "retrieving " + Integer.toString(cuisines.size()));
    }

    public void resetPassword(String name, String email, String password, String newPassword){
        FirebaseUser user = auth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
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
                        if (task.isSuccessful()){
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

    public void createRecipe(String cuisineId, String name, String content, Float rating){
        Recipe recipe = new Recipe(cuisineId, name, content, rating);
        DatabaseReference ref = db.getReference("Recipes");
        String id = ref.push().getKey();
        ref.child(id).setValue(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(application.getApplicationContext(), "Recipe has been added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(application.getApplicationContext(), "(DB) Failed to add the recipe!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteRecipe(String id){
        DatabaseReference ref = db.getReference("Recipes");
        ref.child(id).removeValue();
    }

    public void updateRecipe(String id, Map<String, Object> newRecipe){
        DatabaseReference ref = db.getReference("Recipes");
        ref.child(id).updateChildren(newRecipe)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(application.getApplicationContext(), "Recipe Updated Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Failed to login!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public FirebaseRecyclerOptions<Recipe> retrieveRecipes(){
        DatabaseReference ref = db.getReference("Recipes");
        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(ref, Recipe.class)
                .build();
        return options;
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

}

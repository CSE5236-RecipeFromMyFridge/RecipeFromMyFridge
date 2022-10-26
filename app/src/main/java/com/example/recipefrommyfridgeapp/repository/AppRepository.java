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
import com.example.recipefrommyfridgeapp.model.User;
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
import java.util.List;

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
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
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
                            mUserMutableLiveData.postValue(auth.getCurrentUser());
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

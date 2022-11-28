package com.example.recipefrommyfridgeapp.ui.login;

import android.content.Intent;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.User;
import com.example.recipefrommyfridgeapp.ui.cuisine.CuisineActivity;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientActivity;
import com.example.recipefrommyfridgeapp.ui.recipe.SavedRecipeActivity;
import com.example.recipefrommyfridgeapp.viewmodel.LoggedInViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoggedInFragment extends Fragment implements  View.OnClickListener{

    private Button changeIngredientButton, changeCuisineButton, accountButton, logOutButton, savedRecipeButton;
    private TextView greetingUser;
    private String userId = "";

    private LoggedInViewModel loggedInViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedInViewModel = new ViewModelProvider(this).get(LoggedInViewModel.class);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logged_in, container, false);
        Log.i("checkpoint5", "LoggedInFragment.onCreateView()");

        changeIngredientButton = v.findViewById(R.id.fragment_logged_in_change_ingredient);
        changeCuisineButton = v.findViewById(R.id.fragment_logged_in_change_cuisine);
        accountButton = v.findViewById(R.id.fragment_logged_in_my_account);
        savedRecipeButton = v.findViewById(R.id.fragment_logged_in_saved_recipe);
        logOutButton = v.findViewById(R.id.fragment_logged_in_log_out);
        greetingUser = v.findViewById(R.id.fragment_logged_in_greeting);
        changeIngredientButton.setOnClickListener(this);
        changeCuisineButton.setOnClickListener(this);
        accountButton.setOnClickListener(this);
        logOutButton.setOnClickListener(this);
        loggedInViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    userId = "" + firebaseUser.getUid();
                    savedRecipeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = SavedRecipeActivity.newIntent(getActivity(), userId);
                            startActivity(intent);
                        }
                    });
                    final FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference ref = db.getReference("Users").child(firebaseUser.getUid());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            Log.d("checkpoint5", "the user name retrieved");
                            greetingUser.setText(getResources().getText(R.string.greeting) + ", " + user.getName());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("checkpoint5", "fail to show the user name");
                        }
                    });}
            }
        });

        loggedInViewModel.getLoggedOutMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut){
                    getParentFragmentManager().beginTransaction()
                            .remove(LoggedInFragment.this)
                            .commit();
                    getParentFragmentManager().popBackStack();
                }
            }
        });



        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_logged_in_change_ingredient:
                startActivity(new Intent(getContext(), ChooseIngredientActivity.class));
                break;
            case R.id.fragment_logged_in_change_cuisine:
                startActivity(new Intent(getContext(), CuisineActivity.class));
                break;
            case R.id.fragment_logged_in_my_account:
                startActivity(new Intent(getContext(), MyAccountActivity.class));
                break;
            case R.id.fragment_logged_in_log_out:
                loggedInViewModel.logOut(userId);
                break;
        }
    }
}
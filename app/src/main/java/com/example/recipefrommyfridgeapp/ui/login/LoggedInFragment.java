package com.example.recipefrommyfridgeapp.ui.login;

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
import com.example.recipefrommyfridgeapp.viewmodel.LoggedInViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInFragment extends Fragment implements  View.OnClickListener{

    private Button changeIngredientButton, changeCuisineButton, accountButton, logOutButton, savedRecipeButton;
    private TextView greetingUser;

    private LoggedInViewModel loggedInViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loggedInViewModel = new ViewModelProvider(this).get(LoggedInViewModel.class);
        loggedInViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    greetingUser.setText("Hello, " + firebaseUser.getEmail());
                }
            }
        });

        loggedInViewModel.getLoggedOutMutableLiveData().observe(this, new Observer<Boolean>() {
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
        savedRecipeButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_logged_in_change_ingredient:
            case R.id.fragment_logged_in_change_cuisine:
            case R.id.fragment_logged_in_my_account:
            case R.id.fragment_logged_in_saved_recipe:
            case R.id.fragment_logged_in_log_out:
                loggedInViewModel.logOut();
                break;
        }
    }
}

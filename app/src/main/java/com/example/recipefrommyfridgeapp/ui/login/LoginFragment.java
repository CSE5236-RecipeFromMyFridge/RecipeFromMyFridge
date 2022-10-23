package com.example.recipefrommyfridgeapp.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.ui.ingredient.ChooseIngredientActivity;
import com.example.recipefrommyfridgeapp.viewmodel.LoginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText usernameEditText, passwordEditText;
    private Button loginButton, createAccountButton, guestAccountButton;
    private ProgressBar loadingProgressBar;

    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint2", "LoginFragment.onCreate()");
        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        loginRegisterViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    Toast.makeText(getContext(), "User created", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Log.i("checkpoint2", "LoginFragment.onCreateView()");

        usernameEditText = v.findViewById(R.id.fragment_login_email);
        passwordEditText = v.findViewById(R.id.fragment_login_password);
        loginButton = v.findViewById(R.id.fragment_login_login);
        createAccountButton = v.findViewById(R.id.fragment_login_createAccount);
        guestAccountButton = v.findViewById(R.id.fragment_login_guestAccount);
        loadingProgressBar = v.findViewById(R.id.fragment_login_loading);

        createAccountButton.setOnClickListener(view -> {
            //replace with account creation fragment
            Fragment fragment = new AccountCreationFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .setReorderingAllowed(true)
                    .addToBackStack("Account Creation")
                    .commit();
        });

        loginButton.setOnClickListener(this);
        guestAccountButton.setOnClickListener(this);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_login_guestAccount:
                startActivity(new Intent(requireContext(), ChooseIngredientActivity.class));
                break;
            case R.id.fragment_login_login:
                loginUser();
                startActivity(new Intent(requireContext(), ChooseIngredientActivity.class));
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void loginUser(){
        String userName = usernameEditText.getText().toString().trim();
        String userPassword = passwordEditText.getText().toString().trim();
        if (userName.isEmpty()){
            usernameEditText.setError("User Name is required!");
            usernameEditText.requestFocus();
            return;
        }
        if (userPassword.isEmpty()){
            passwordEditText.setError("User Password is required!");
            passwordEditText.requestFocus();
            return;
        }
        loadingProgressBar.setVisibility(View.VISIBLE);
        loginRegisterViewModel.login(userName, userPassword);
    }





}
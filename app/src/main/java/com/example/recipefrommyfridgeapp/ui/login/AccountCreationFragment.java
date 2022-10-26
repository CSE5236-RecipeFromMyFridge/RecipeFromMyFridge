package com.example.recipefrommyfridgeapp.ui.login;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.viewmodel.LoginRegisterViewModel;


public class AccountCreationFragment extends Fragment implements View.OnClickListener {

    private Button createAccountButton, backButton;
    private EditText name, email, password;

    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint5", "AccountCreationFragment.onCreate()");
        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account_creation, container, false);
        Log.i("checkpoint2", "AccountCreationFragment.onCreateView()");

        createAccountButton = v.findViewById(R.id.fragment_account_creation_createAccountPage);
        backButton = v.findViewById(R.id.fragment_account_creation_back);
        name = v.findViewById(R.id.fragment_account_creation_user_name);
        email = v.findViewById(R.id.fragment_account_creation_email);
        password = v.findViewById(R.id.fragment_account_creation_password);
        createAccountButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_account_creation_back:
                getParentFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
                getParentFragmentManager().popBackStack();
                break;
            case R.id.fragment_account_creation_createAccountPage:
                createUser();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void createUser(){
        String userName = name.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        if (userName.isEmpty()){
            name.setError("User Name is required!");
            name.requestFocus();
            return;
        }
        if (userEmail.isEmpty()){
            email.setError("User Email is required!");
            email.requestFocus();
            return;
        }
        if (userPassword.isEmpty()){
            password.setError("User Password is required!");
            password.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.setError("Invalid Email!");
            email.requestFocus();
            return;
        }
        if (userPassword.length() < 6){
            password.setError("Password should be at least 6 characters!");
            password.requestFocus();
            return;
        }
        loginRegisterViewModel.register(userName, userEmail, userPassword);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("checkpoint2", "AccountCreationFragment.onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("checkpoint2", "AccountCreationFragment.onStop()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("checkpoint2", "AccountCreationFragment.onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("checkpoint2", "AccountCreationFragment.onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("checkpoint2", "AccountCreationFragment.onDestroy()");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i("checkpoint2", "AccountCreationFragment.onAttach()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("checkpoint2", "AccountCreationFragment.onDetach()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("checkpoint2", "AccountCreationFragment.onDestroyView()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("checkpoint2", "AccountCreationFragment.onActivityCreated()");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i("checkpoint2", "AccountCreationFragment.onViewStateRestored()");
    }
}
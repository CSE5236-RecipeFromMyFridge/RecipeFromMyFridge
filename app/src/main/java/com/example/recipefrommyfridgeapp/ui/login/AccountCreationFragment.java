package com.example.recipefrommyfridgeapp.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class AccountCreationFragment extends Fragment implements View.OnClickListener {

    private Button createAccountButton, backButton;
    private EditText name, email, password;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account_creation, container, false);
        Log.i("checkpoint2", "AccountCreationFragment.onCreateView()");

        mAuth = FirebaseAuth.getInstance();
        createAccountButton = v.findViewById(R.id.createAccountPage);
        backButton = v.findViewById(R.id.back);
        name = v.findViewById(R.id.user_name);
        email = v.findViewById(R.id.email);
        password = v.findViewById(R.id.password);
        createAccountButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        progressBar = v.findViewById(R.id.progress_circular);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                getParentFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
                getParentFragmentManager().popBackStack();
                break;
            case R.id.createAccountPage:
                createUser();
                break;
        }
    }

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
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(userName, userEmail, userPassword);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(getContext(), "User has been registered successfully!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(getContext(), "Failed to register!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                    } else {
                            Toast.makeText(getContext(), "Failed to register!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                }

        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("checkpoint2", "AccountCreationFragment.onCreate()");
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
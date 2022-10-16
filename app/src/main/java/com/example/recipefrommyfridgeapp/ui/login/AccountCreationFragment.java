package com.example.recipefrommyfridgeapp.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.R;


public class AccountCreationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account_creation, container, false);
        Log.i("checkpoint2", "AccountCreationFragment.onCreateView()");

        final Button createAccountButton = v.findViewById(R.id.createAccount);

//         TODO: enable button after validation of fields - fields are filled
        createAccountButton.setEnabled(true);

        createAccountButton.setOnClickListener(view -> {
            //TODO: check if account successfully created
            //navigate back to login
            getParentFragmentManager().beginTransaction()
                    .remove(this)
                    .commit();
            getParentFragmentManager().popBackStack();
        });

        return v;
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
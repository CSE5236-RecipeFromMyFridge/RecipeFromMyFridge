package com.example.recipefrommyfridgeapp.ui.login;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.SingleFragmentActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class LoginActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Log.d("checkpoint6", "onCreate: " + Locale.getDefault().getLanguage());
        Log.i("checkpoint2", "LoginActivity.onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("checkpoint2", "LoginActivity.onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("checkpoint2", "LoginActivity.onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("checkpoint2", "LoginActivity.onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("checkpoint2", "LoginActivity.onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("checkpoint2", "LoginActivity.onDestroy()");
    }
}
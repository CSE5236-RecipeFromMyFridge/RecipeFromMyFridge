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

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.viewmodel.LoginRegisterViewModel;

import org.w3c.dom.Text;

public class LoggedInFragment extends Fragment implements  View.OnClickListener{

    private Button changeIngredientButton, changeCuisineButton, accountButton, logOutButton;
    private TextView greetingUser;

    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logged_in, container, false);
        Log.i("checkpoint5", "LoggedInFragment.onCreateView()");

        changeIngredientButton = v.findViewById(R.id.fragment_logged_in_change_ingredient);
        changeCuisineButton = v.findViewById(R.id.fragment_logged_in_change_cuisine);
        accountButton = v.findViewById(R.id.fragment_logged_in_my_account);
        logOutButton = v.findViewById(R.id.fragment_logged_in_log_out);
        greetingUser = v.findViewById(R.id.fragment_logged_in_greeting);
        changeIngredientButton.setOnClickListener(this);
        changeCuisineButton.setOnClickListener(this);
        accountButton.setOnClickListener(this);
        logOutButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

    }
}

package com.example.recipefrommyfridgeapp.ui.cuisine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefrommyfridgeapp.R;

public class CuisineItemFragment extends Fragment {

    private final String mCuisine;

    CuisineItemFragment(String cuisine) {
        mCuisine = cuisine;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cuisine_item, container, false);
        CheckBox checkBox = v.findViewById(R.id.cuisine_checkbox);
        checkBox.setText(mCuisine);
        return v;
    }
}

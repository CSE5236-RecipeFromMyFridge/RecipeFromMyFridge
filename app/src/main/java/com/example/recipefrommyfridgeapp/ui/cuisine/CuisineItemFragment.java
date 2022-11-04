package com.example.recipefrommyfridgeapp.ui.cuisine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.viewmodel.CuisineViewModel;

import java.util.Set;

public class CuisineItemFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private final String mCuisine;
    private final String mType;
    private CuisineViewModel mCuisineViewModel;
    private Set<String> mCuisineSelected;

    CuisineItemFragment(String cuisine, String type) {
        mCuisine = cuisine;
        mType = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCuisineViewModel = new ViewModelProvider(requireParentFragment()).get(CuisineViewModel.class);
        mCuisineSelected = mCuisineViewModel.getCuisineSelectedMutableLiveData().getValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cuisine_item, container, false);
        CheckBox checkBox = v.findViewById(R.id.cuisine_checkbox);
        checkBox.setText(mCuisine + " - " + mType);
        checkBox.setOnCheckedChangeListener(this);
        return v;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.cuisine_checkbox) {
            if (b) {
                mCuisineSelected.add(mType);
            } else {
                mCuisineSelected.remove(mType);
            }
        }
    }
}

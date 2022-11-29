package com.example.recipefrommyfridgeapp.ui.cuisine;

import android.os.Bundle;
import android.util.Log;
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
import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.viewmodel.CuisineViewModel;

import java.util.Set;

public class CuisineItemFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static Cuisine mCuisine;
    private CuisineViewModel mCuisineViewModel;
    private Set<String> mCuisineSelected;

    public CuisineItemFragment() {
    }

    public static CuisineItemFragment newInstance(Cuisine cuisine) {
        Bundle args = new Bundle();
        args.putParcelable("cuisine", cuisine);
        CuisineItemFragment fragment = new CuisineItemFragment();
        fragment.setArguments(args);
        return fragment;
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
        mCuisine = getArguments().getParcelable("cuisine");
        checkBox.setText(mCuisine.getZhName() + " - " + mCuisine.getEnName());
        checkBox.setOnCheckedChangeListener(this);
        return v;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.cuisine_checkbox) {
            String find = compoundButton.getText().toString();
            Log.d("checkpoint6", "find: " + find);
            Log.d("checkpoint6", "mCuisine is: " + mCuisine.getName());
            if (b) {
//                mCuisineSelected.add(mCuisine.getEnName());
                mCuisineSelected.add(find);
            } else {
//                mCuisineSelected.remove(mCuisine.getEnName());
                mCuisineSelected.remove(find);
            }
            Log.d("checkpoint6", mCuisineSelected.toString());
        }
    }
}
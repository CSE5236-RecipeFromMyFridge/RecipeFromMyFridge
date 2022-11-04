package com.example.recipefrommyfridgeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recipefrommyfridgeapp.model.Cuisine;
import com.example.recipefrommyfridgeapp.repository.AppRepository;

import java.util.List;

public class CuisineViewModel extends AndroidViewModel {

    private final AppRepository mAppRepository;
    private final MutableLiveData<List<Cuisine>> mCuisineMutableLiveData;
    private final MutableLiveData<StringBuilder> mCuisineSelectedMutableLiveData;


    public CuisineViewModel(@NonNull Application application) {
        super(application);
        mAppRepository = new AppRepository(application);
        mCuisineMutableLiveData = mAppRepository.getCuisineMutableLiveData();
        mCuisineSelectedMutableLiveData = new MutableLiveData<>();
    }

    public void retrieveCuisines() {
        mAppRepository.retrieveCuisines();
    }

    public MutableLiveData<List<Cuisine>> getCuisineMutableLiveData() {
        return mCuisineMutableLiveData;
    }

    public MutableLiveData<StringBuilder> getCuisineSelectedMutableLiveData() {
        return mCuisineSelectedMutableLiveData;
    }

    public void setCuisineSelectedMutableLiveData(StringBuilder cuisine) {
        mCuisineSelectedMutableLiveData.setValue(cuisine);
    }

    public void addCuisineSelected(String cuisine) {
        StringBuilder s = mCuisineSelectedMutableLiveData.getValue();
        if (s.length() == 0) {
            s.append(cuisine);
        } else {
            s.append("," + cuisine);
        }
        mCuisineSelectedMutableLiveData.setValue(s);
    }

    public void removeCuisineSelected(String cuisine) {
        StringBuilder s = mCuisineSelectedMutableLiveData.getValue();
        int i = s.indexOf(cuisine);
        i = i == 0 ? i : i - 1; //check if it is at the start else remove comma as well
        s.delete(i, i + cuisine.length() + 1);
        while (s.length() > 0 && s.charAt(0) == ',') {
            s.deleteCharAt(0);
        }
        mCuisineSelectedMutableLiveData.setValue(s);

    }
}

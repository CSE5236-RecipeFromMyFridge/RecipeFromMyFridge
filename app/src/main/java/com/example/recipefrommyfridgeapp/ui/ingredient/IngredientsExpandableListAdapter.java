package com.example.recipefrommyfridgeapp.ui.ingredient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Ingredient;

import java.util.List;
import java.util.Map;

public class IngredientsExpandableListAdapter extends BaseExpandableListAdapter implements View.OnClickListener {
    Map<String, List<Ingredient>> mIngredients;
    List<String> mIngredientGroup;
    Context mContext;

    public IngredientsExpandableListAdapter(Context context, Map<String, List<Ingredient>> ingredients, List<String> ingredientGroup) {
        mContext = context;
        mIngredients = ingredients;
        mIngredientGroup = ingredientGroup;
    }

    @Override
    public int getGroupCount() {
        return mIngredientGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mIngredients.get(getGroup(i)).size();
    }

    @Override
    public String getGroup(int i) {
        return mIngredientGroup.get(i);
    }

    @Override
    public String getChild(int i, int i1) {
        return mIngredients.get(getGroup(i)).get(i1).getName();
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.fragment_expandable_group_ingredient, viewGroup, false);
        }
        TextView groupName = view.findViewById(R.id.ingredient_group_title);
        groupName.setText(getGroup(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.fragment_checkbox_ingredient, viewGroup, false);
        }
        CheckedTextView ingredient = view.findViewById(R.id.ingredient_checkmark);
        ingredient.setText(getChild(i, i1));
        ingredient.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void onClick(View view) {
        //TODO: checkmark is not changing when clicked
        if (view.getId() == R.id.ingredient_checkmark) {
            CheckedTextView ingredient = view.findViewById(R.id.ingredient_checkmark);
            Log.i("testing", "onClick: before - " + ingredient.isChecked());
            ingredient.setChecked(!ingredient.isChecked());
            Log.i("testing", "onClick: after - " + ingredient.isChecked());
            notifyDataSetChanged();
        }
    }

    public void updateItems(Map<String, List<Ingredient>> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    public void updateItems(List<String> ingredientGroup) {
        mIngredientGroup = ingredientGroup;
        notifyDataSetChanged();
    }
}

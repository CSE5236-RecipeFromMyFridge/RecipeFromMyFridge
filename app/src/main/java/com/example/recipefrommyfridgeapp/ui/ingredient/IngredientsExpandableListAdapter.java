package com.example.recipefrommyfridgeapp.ui.ingredient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.model.Ingredient;

import java.util.List;
import java.util.Map;

public class IngredientsExpandableListAdapter extends BaseExpandableListAdapter implements CompoundButton.OnCheckedChangeListener {
    Map<String, List<Ingredient>> mIngredients;
    List<String> mIngredientGroup;
    Context mContext;
    StringBuilder mIngredientSelected;
    //TODO: checked position changes when other groups are expanded

    public IngredientsExpandableListAdapter(Context context, Map<String, List<Ingredient>> ingredients, List<String> ingredientGroup, StringBuilder ingredientSelected) {
        mContext = context;
        mIngredients = ingredients;
        mIngredientGroup = ingredientGroup;
        mIngredientSelected = ingredientSelected;
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
            view = layoutInflater.inflate(R.layout.fragment_expandable_list_item_ingredient, viewGroup, false);
        }
        CheckBox ingredient = view.findViewById(R.id.ingredient_checkmark);
        ingredient.setText(getChild(i, i1));
        ingredient.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.ingredient_checkmark) {
            if (b) {
                addIngredient(compoundButton.getText().toString());
            } else {
                removeIngredient(compoundButton.getText().toString());
            }
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

    public void addIngredient(String ingredient) {
        if (mIngredientSelected.length() == 0) {
            mIngredientSelected.append(ingredient);
        } else {
            mIngredientSelected.append("," + ingredient);
        }
    }

    public void removeIngredient(String ingredient) {
        int i = mIngredientSelected.indexOf(ingredient);
        i = i <= 0 ? 0 : i - 1; //check if it is at the start else remove comma as well
        mIngredientSelected.delete(i, i + ingredient.length() + 1);
        while (mIngredientSelected.length() > 0 && mIngredientSelected.charAt(0) == ',') {
            mIngredientSelected.deleteCharAt(0);
        }
    }
}

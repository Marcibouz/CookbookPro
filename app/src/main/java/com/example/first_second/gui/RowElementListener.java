package com.example.first_second.gui;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class RowElementListener implements View.OnClickListener{
    private Fragment fragment;
    private String id;
    private String recipe_name;
    private String ingredients;
    private String directions;
    public RowElementListener(String id, String recipe_name, String ingredients, String directions,
                              Fragment fragment){
        this.id = id;
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
        this.directions = directions;
        this.fragment = fragment;
    }
    @Override
    public void onClick(View view) {
        RecipeListScreenDirections.RecipeListScreenToRecipeScreen action =
                (RecipeListScreenDirections.RecipeListScreenToRecipeScreen)
                        RecipeListScreenDirections.RecipeListScreenToRecipeScreen(id, recipe_name,
                                ingredients, directions);
        NavHostFragment.findNavController(fragment).navigate(action);
    }
}

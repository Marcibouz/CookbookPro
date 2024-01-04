package com.example.first_second.gui;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.first_second.R;
import com.example.first_second.local_memory.DatabaseHelper;

public class UpdateButtonListener implements View.OnClickListener{
    Context context;
    Fragment fragment;
    String id;
    EditText recipe_name_input, ingredients_input, directions_input;
    public UpdateButtonListener(Context context, Fragment fragment, String id,
                                EditText recipe_name_input, EditText ingredients_input,
                                EditText directions_input){
        this.context = context;
        this.fragment = fragment;
        this.id = id;
        this.recipe_name_input = recipe_name_input;
        this.ingredients_input = ingredients_input;
        this.directions_input = directions_input;
    }

    @Override
    public void onClick(View view) {
        DatabaseHelper db = new DatabaseHelper(context);
        String recipe_name = recipe_name_input.getText().toString().trim();
        String ingredients = ingredients_input.getText().toString().trim();
        String directions = directions_input.getText().toString().trim();
        long addFeedback = db.updateRecipe(id, recipe_name, ingredients, directions);

        if(addFeedback == -1){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }else{
            NavHostFragment.findNavController(fragment).
                    navigate(R.id.RecipeScreen_to_RecipeListScreen);
            Toast.makeText(context, "Recipe updated!", Toast.LENGTH_SHORT).show();
        }
    }
}

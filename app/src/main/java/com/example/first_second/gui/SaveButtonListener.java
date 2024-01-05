package com.example.first_second.gui;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.first_second.R;
import com.example.first_second.local_memory.DatabaseHelper;

//Kriegt die EditTexts sowie den Context übergeben. Führt mit diesen die addRecipe-Methode aus
public class SaveButtonListener implements View.OnClickListener{
    private Context context;
    private EditText recipe_name, ingredients, directions;
    private Fragment fragment;
    public SaveButtonListener(Context context, EditText recipe_name, EditText ingredients,
                              EditText directions, Fragment fragment){
        this.context = context;
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
        this.directions = directions;
        this.fragment = fragment;
    }

    //Mit dem Context wird das DatabaseHelper Objekt erzeugt, auf welchem mit den Strings aus dem
    //Edittext die addRecipe Methode aufgerufen wird. Das Fragment wird verwendet, um bei
    //erfolgreichem Hinzufügen wieder zurück zum Recipelistscreen zu gelangen.
    @Override
    public void onClick(View view) {
        DatabaseHelper db = new DatabaseHelper(context);
        long saveFeedback = db.addRecipe(recipe_name.getText().toString().trim(),
                ingredients.getText().toString().trim(),
                directions.getText().toString().trim());

        if (saveFeedback == -1){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }else{
            NavHostFragment.findNavController(fragment).navigate(R.id.AddScreen_to_RecipeListScreen);
            Toast.makeText(context, "Recipe added!", Toast.LENGTH_SHORT).show();

            //Actionbar Title setzen
            ActionBar actionBar = ((AppCompatActivity)fragment.getActivity()).getSupportActionBar();
            actionBar.setTitle("Cookbook Pro");
        }
    }
}

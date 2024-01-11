package com.example.first_second.gui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.first_second.R;
import com.example.first_second.databinding.AddscreenBinding;
import com.example.first_second.local_memory.DatabaseHelper;
import com.example.first_second.local_memory.LocalMemory;
import com.example.first_second.local_memory.Recipe;

public class AddScreen extends Fragment {

    private AddscreenBinding binding;
    private Context context;
    private EditText recipe_name;
    private EditText ingredients;
    private EditText directions;
    private Button save_button;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddscreenBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    //Setzt die Instanzvariablen und den saveButtonListener auf den save_button
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();

        recipe_name = view.findViewById(R.id.recipe_name);
        ingredients = view.findViewById(R.id.ingredients);
        directions = view.findViewById(R.id.directions);
        save_button = view.findViewById(R.id.save_button);

        SaveButtonListener saveButtonListener = new SaveButtonListener();
        save_button.setOnClickListener(saveButtonListener);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class SaveButtonListener implements View.OnClickListener{
        //Mit dem Context wird das DatabaseHelper Objekt erzeugt, auf welchem mit den Strings aus dem
        //Edittext die addRecipe Methode aufgerufen wird. Das Fragment wird verwendet, um bei
        //erfolgreichem Hinzufügen wieder zurück zum Recipelistscreen zu gelangen.
        @Override
        public void onClick(View view) {
            LocalMemory lm = DatabaseHelper.getDatabaseHelper(context);
            Recipe recipe = new Recipe(recipe_name.getText().toString().trim(),
                    ingredients.getText().toString().trim(), directions.getText().toString().trim());
            long saveFeedback = lm.addRecipe(recipe);

            if (saveFeedback == -1){
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }else{
                NavHostFragment.findNavController(AddScreen.this).
                        navigate(R.id.AddScreen_to_RecipeListScreen);
                Toast.makeText(context, "Recipe added!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
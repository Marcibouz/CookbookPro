package com.example.first_second.gui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.first_second.R;
import com.example.first_second.databinding.RecipescreenBinding;


public class RecipeScreen extends Fragment {
    private RecipescreenBinding binding;
    private Context context;
    EditText recipe_name_input, ingredients_input, directions_input;
    Button update_button;
    String id, recipe_name, ingredients, directions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RecipescreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();

        recipe_name_input = view.findViewById(R.id.recipe_name2);
        ingredients_input = view.findViewById(R.id.ingredients2);
        directions_input = view.findViewById(R.id.directions2);
        update_button = view.findViewById(R.id.update_button);

        getAndSetArgData();

        //hier wieder addonclicklistener für update button
        UpdateButtonListener updateButtonListener = new UpdateButtonListener(context,
                RecipeScreen.this, id, recipe_name_input, ingredients_input,
                directions_input);
        update_button.setOnClickListener(updateButtonListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getAndSetArgData(){
        //Getting Data from Args
        id = RecipeScreenArgs.fromBundle(getArguments()).getId();
        recipe_name = RecipeScreenArgs.fromBundle(getArguments()).getRecipeName();
        ingredients = RecipeScreenArgs.fromBundle(getArguments()).getIngredients();
        directions = RecipeScreenArgs.fromBundle(getArguments()).getDirections();

        //Setting Edittexts
        recipe_name_input.setText(recipe_name);
        ingredients_input.setText(ingredients);
        directions_input.setText(directions);

    }
}
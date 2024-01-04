package com.example.first_second.gui;

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
    EditText recipe_name_input, ingredients_input, directions_input;
    Button update_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RecipescreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipe_name_input = view.findViewById(R.id.recipe_name2);
        ingredients_input = view.findViewById(R.id.ingredients2);
        directions_input = view.findViewById(R.id.directions2);
        update_button = view.findViewById(R.id.update_button);
        //hier wieder addonclicklistener
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
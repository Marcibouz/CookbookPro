package com.example.first_second.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.first_second.R;
import com.example.first_second.databinding.AddscreenBinding;
import com.example.first_second.local_memory.DatabaseHelper;

public class AddScreen extends Fragment {

    private AddscreenBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = AddscreenBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    //Setzt die Instanzvariablen und den saveButtonListener auf den save_button
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText recipe_name = view.findViewById(R.id.recipe_name);
        EditText ingredients = view.findViewById(R.id.ingredients);
        EditText directions = view.findViewById(R.id.directions);
        Button save_button = view.findViewById(R.id.save_button);

        SaveButtonListener saveButtonListener = new SaveButtonListener(getContext(), recipe_name,
                ingredients, directions, AddScreen.this);
        save_button.setOnClickListener(saveButtonListener);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
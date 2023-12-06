package com.example.first_second.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_second.R;
import com.example.first_second.databinding.RecipelistscreenBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipeListScreen extends Fragment {

    private RecipelistscreenBinding binding;
    private TextView lol;
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = RecipelistscreenBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        add_button = view.findViewById(R.id.add_button);

        AddButtonListener addButtonListener = new AddButtonListener(RecipeListScreen.this);
        add_button.setOnClickListener(addButtonListener);
//        binding.viewrecipesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(HomeScreen.this)
//                       .navigate(R.id.HomeScreen_to_RecipeListScreen);
//            }
//        });
    }

    private void countMe(TextView text) {
        // Get the value of the text view
        String countString = text.getText().toString();
        // Convert value to a number and increment it
        int count = Integer.parseInt(countString);
        count++;
        // Display the new value in the text view.
        text.setText(Integer.toString(count));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
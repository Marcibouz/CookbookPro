package com.example.first_second.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.first_second.R;
import com.example.first_second.databinding.HomescreenBinding;

public class HomeScreen extends Fragment {

    private HomescreenBinding binding;
    private TextView lol;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = HomescreenBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.viewrecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeScreen.this)
                       .navigate(R.id.HomeScreen_to_RecipeListScreen);
            }
        });
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
package com.example.first_second.gui;

import android.os.Bundle;
import android.view.MenuInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.example.first_second.R;
import com.example.first_second.databinding.ActivityMainBinding;

public class DestinationChangedListener implements NavController.OnDestinationChangedListener {
    private ActivityMainBinding binding;
    private MenuInflater menuInflater;
    public DestinationChangedListener(ActivityMainBinding binding, MenuInflater menuInflater){
        this.binding = binding;
        this.menuInflater = menuInflater;
    }
    @Override
    public void onDestinationChanged(@NonNull NavController navController,
                                     @NonNull NavDestination currentDestination,
                                     @Nullable Bundle bundle) {
        binding.toolbar.getMenu().clear();
        int currentFragmentId = currentDestination.getId();
        if (currentFragmentId == R.id.RecipeListScreen){
            menuInflater.inflate(R.menu.menu_main, binding.toolbar.getMenu());
        }
    }
}

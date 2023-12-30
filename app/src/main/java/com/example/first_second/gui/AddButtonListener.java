package com.example.first_second.gui;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.first_second.R;

public class AddButtonListener implements View.OnClickListener {
    Fragment fragment;
    public AddButtonListener(Fragment fragment){
        this.fragment=fragment;
    }

    //Geht zum AddScreen
    @Override
    public void onClick(View view) {
        NavHostFragment.findNavController(fragment).navigate(R.id.RecipeListScreen_to_AddScreen);
    }
}

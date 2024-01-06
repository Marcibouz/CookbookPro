package com.example.first_second.gui;

import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.first_second.R;

public class SplashScreenListener implements View.OnClickListener {
    private Fragment fragment;
    public SplashScreenListener(Fragment fragment){
        this.fragment = fragment;
    }
    @Override
    public void onClick(View view) {
        NavHostFragment.findNavController(fragment).navigate(R.id.SplashScreen_to_RecipeListScreen);

        //Actionbar Title setzen
        ActionBar actionBar = ((AppCompatActivity)fragment.getActivity()).getSupportActionBar();
        actionBar.setTitle("Cookbook Pro");
    }
}

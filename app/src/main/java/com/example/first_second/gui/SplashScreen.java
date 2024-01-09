package com.example.first_second.gui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.first_second.R;
import com.example.first_second.databinding.SplashscreenBinding;


public class SplashScreen extends Fragment {
    private SplashscreenBinding binding;
    private FrameLayout splashScreenLayout;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SplashscreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        splashScreenLayout = view.findViewById(R.id.splashScreen);

        ///setOnclicklistener für splashScreenLayout;
        SplashscreenListener splashScreenListener =
                new SplashscreenListener();
        splashScreenLayout.setOnClickListener(splashScreenListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private class SplashscreenListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            NavHostFragment.
                    findNavController(SplashScreen.this).
                    navigate(R.id.SplashScreen_to_RecipeListScreen);
        }
    }
}
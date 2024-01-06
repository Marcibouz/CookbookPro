package com.example.first_second.gui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        splashScreenLayout = view.findViewById(R.id.splashScreen);

        ///setOnclicklistener für splashScreenLayout;
        SplashScreenListener splashScreenListener =
                new SplashScreenListener(SplashScreen.this);
        splashScreenLayout.setOnClickListener(splashScreenListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
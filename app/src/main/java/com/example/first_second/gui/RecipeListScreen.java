package com.example.first_second.gui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_second.R;
import com.example.first_second.databinding.RecipelistscreenBinding;
import com.example.first_second.memory.MemoryImpl;
import com.example.first_second.memory.Memory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipeListScreen extends Fragment {

    private RecipelistscreenBinding binding;
    private Context context;
    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private ConstraintLayout recipeListLayout;
    private Drawable nothingHereYetBackground;
    private Drawable emptyTableBackground;
    private Memory memory;
    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = RecipelistscreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    //Setzt die Instanzvariablen und den addButtonListener auf den add_Button.
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Context setzen
        context = getContext();

        //Views initialisieren
        recyclerView = view.findViewById(R.id.recyclerViewRecipes);
        add_button = view.findViewById(R.id.add_button);
        recipeListLayout = view.findViewById(R.id.recipeListScreen);

        //Images initialisieren
        nothingHereYetBackground =
                ResourcesCompat.getDrawable(getResources(), R.drawable.nothinghereyet, null);
        emptyTableBackground =
                ResourcesCompat.getDrawable(getResources(), R.drawable.emtpytable, null);

        //AddButtonlistener setzen
        AddButtonListener addButtonListener = new AddButtonListener();
        add_button.setOnClickListener(addButtonListener);

        recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(context,
                RecipeListScreen.this, recipeListLayout, nothingHereYetBackground,
                emptyTableBackground);
        memory = MemoryImpl.getMemoryImpl(context);
        memory.addNewDatabaseObserver(recipeRecyclerViewAdapter);
        recyclerView.setAdapter(recipeRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class AddButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            NavHostFragment.
                    findNavController(RecipeListScreen.this).
                    navigate(R.id.RecipeListScreen_to_AddScreen);
        }
    }
}
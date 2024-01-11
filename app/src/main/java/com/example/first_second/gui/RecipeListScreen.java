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
import com.example.first_second.local_memory.DatabaseHelper;
import com.example.first_second.local_memory.LocalMemory;
import com.example.first_second.local_memory.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.List;

public class RecipeListScreen extends Fragment {

    private RecipelistscreenBinding binding;
    private Context context;
    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private ConstraintLayout recipeListLayout;

    private Drawable nothingHereYetBackground;

    private LocalMemory lm;
    private LinkedList<String> recipe_id, recipe_name, recipe_ingredients, recipe_directions;

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

        //Image initialisieren
        nothingHereYetBackground =
                ResourcesCompat.getDrawable(getResources(), R.drawable.nothinghereyet, null);

        //AddButtonlistener setzen
        AddButtonListener addButtonListener = new AddButtonListener();
        add_button.setOnClickListener(addButtonListener);

        //DatabaseHelper initialisieren
        lm = new DatabaseHelper(context);

        //Rezept IDs und Namen in Arrays speichern
        recipe_id = new LinkedList<>();
        recipe_name = new LinkedList<>();
        recipe_ingredients = new LinkedList<>();
        recipe_directions = new LinkedList<>();
        storeRecipesInLists();

        //Diese an recyclerviewadapter geben
        recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(context,
                RecipeListScreen.this, recipe_id, recipe_name, recipe_ingredients,
                recipe_directions);
        recyclerView.setAdapter(recipeRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    /**
     * Nutzt den DatabaseHelper um ein Cursor mit allen Rezepten zu erhalten und packt deren IDs
     * und Namen jeweils in die Arrays.
     */
    private void storeRecipesInLists(){
        List<Recipe> recipes = lm.readAllRecipes();
        if (recipes.isEmpty()){
            recipeListLayout.setBackground(nothingHereYetBackground);
        } else{
            for (Recipe r : recipes){
                recipe_id.add(r.getId());
                recipe_name.add(r.getRecipeName());
                recipe_ingredients.add(r.getIngredients());
                recipe_directions.add(r.getDirections());
            }
        }
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
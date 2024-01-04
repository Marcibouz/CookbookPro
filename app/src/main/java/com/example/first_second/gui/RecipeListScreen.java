package com.example.first_second.gui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_second.R;
import com.example.first_second.databinding.RecipelistscreenBinding;
import com.example.first_second.local_memory.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class RecipeListScreen extends Fragment {

    private RecipelistscreenBinding binding;
    Context context;
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    DatabaseHelper db;
    LinkedList<String> recipe_id, recipe_name, recipe_ingredients, recipe_directions;

    RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = RecipelistscreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    //Setzt die Instanzvariablen und den addButtonListener auf den add_Button.
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Context setzen
        context = getContext();

        //Views initialisieren
        recyclerView = view.findViewById(R.id.recyclerView);
        add_button = view.findViewById(R.id.add_button);

        //AddButtonlistener setzen
        AddButtonListener addButtonListener = new AddButtonListener(RecipeListScreen.this);
        add_button.setOnClickListener(addButtonListener);

        //DatabaseHelper initialisieren
        db = new DatabaseHelper(context);

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
    void storeRecipesInLists(){
        Cursor cursor = db.readAllRecipes();
        if (cursor.getCount() == 0){
            Toast.makeText(context,"No recipes yet.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                recipe_id.add(cursor.getString(0));
                recipe_name.add(cursor.getString(1));
                recipe_ingredients.add(cursor.getString(2));
                recipe_directions.add(cursor.getString(3));
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package com.example.first_second.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_second.R;

import java.util.LinkedList;

public class RecipeRecyclerViewAdapter extends
        RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder>{
    private Context context;
    //fragment muss aus RecipeListScreen weitergegeben werden, damit das fragment dem RowElement-
    //Listener übergeben werden kann, damit dieser dann in den RecipeScreen wechselt und die Rezept-
    //Daten übergibt
    private Fragment fragment;
    private LinkedList<String> recipe_id, recipe_name, recipe_ingredients, recipe_directions;
    public RecipeRecyclerViewAdapter(Context context, Fragment fragment,
                                     LinkedList<String> recipe_id,
                                     LinkedList<String> recipe_name,
                                     LinkedList<String> recipe_ingredients,
                                     LinkedList<String> recipe_directions){
        this.context = context;
        this.fragment = fragment;
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_ingredients = recipe_ingredients;
        this.recipe_directions = recipe_directions;
    }
    public RecipeRecyclerViewAdapter(Context context,
                                     LinkedList<String> recipe_id,
                                     LinkedList<String> recipe_name,
                                     LinkedList<String> recipe_ingredients,
                                     LinkedList<String> recipe_directions){
        this.context = context;
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_ingredients = recipe_ingredients;
        this.recipe_directions = recipe_directions;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_row, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipe_name_txt.setText(recipe_name.get(position));
        RecipeRowElementListener recipeRowElementListener =
                new RecipeRowElementListener(position);
        holder.recipeRowLayout.setOnClickListener(recipeRowElementListener);
    }

    @Override
    public int getItemCount() {
        return recipe_id.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipe_name_txt;
        LinearLayout recipeRowLayout;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_name_txt = itemView.findViewById(R.id.recipe_name_txt);
            recipeRowLayout = itemView.findViewById(R.id.recipeRowLayout);
        }
    }

    private class RecipeRowElementListener implements View.OnClickListener{

        private String current_id;
        private String current_recipe_name;
        private String current_ingredients;
        private String current_directions;
        private RecipeRowElementListener(int position){
            current_id = recipe_id.get(position);
            current_recipe_name = recipe_name.get(position);
            current_ingredients = recipe_ingredients.get(position);
            current_directions = recipe_directions.get(position);
        }
        @Override
        public void onClick(View view) {
            RecipeListScreenDirections.RecipeListScreenToRecipeScreen action =
                    (RecipeListScreenDirections.RecipeListScreenToRecipeScreen)
                            RecipeListScreenDirections.
                                    RecipeListScreenToRecipeScreen(current_id, current_recipe_name,
                                    current_ingredients, current_directions);
            NavHostFragment.findNavController(fragment).navigate(action);
            ActionBar actionBar = ((AppCompatActivity)fragment.getActivity()).getSupportActionBar();
            actionBar.setTitle(current_recipe_name);
        }
    }
}

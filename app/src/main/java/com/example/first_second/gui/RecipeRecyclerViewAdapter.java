package com.example.first_second.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
                new RecipeRowElementListener(recipe_id.get(position), recipe_name.get(position),
                        recipe_ingredients.get(position), recipe_directions.get(position),
                        fragment);
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
}

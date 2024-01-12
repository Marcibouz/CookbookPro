package com.example.first_second.gui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_second.R;
import com.example.first_second.local_memory.LocalMemoryImpl;
import com.example.first_second.local_memory.LocalMemory;
import com.example.first_second.local_memory.Recipe;

import java.util.LinkedList;
import java.util.List;

public class RecipeRecyclerViewAdapter extends
        RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder> implements
        DatabaseObserver{
    private Context context;
    private Fragment fragment;
    private ConstraintLayout recipeListLayout;
    private Drawable nothingHereYetBackground;
    private LinkedList<String> recipe_id = new LinkedList<>();
    private LinkedList<String> recipe_name = new LinkedList<>();
    private LinkedList<String> recipe_ingredients = new LinkedList<>();
    private LinkedList<String> recipe_directions = new LinkedList<>();
    public RecipeRecyclerViewAdapter(Context context, Fragment fragment,
                                     ConstraintLayout recipeListLayout,
                                     Drawable nothingHereYetBackground){
        this.context = context;
        this.fragment = fragment;
        this.recipeListLayout = recipeListLayout;
        this.nothingHereYetBackground = nothingHereYetBackground;
        recipeListChanged();
    }

    @Override
    public void recipeListChanged() {
        LocalMemory lm = LocalMemoryImpl.getDatabaseHelper(context);
        List<Recipe> recipes = lm.readAllRecipes();

        recipe_id.clear();
        recipe_name.clear();
        recipe_ingredients.clear();
        recipe_directions.clear();

        if (recipes.isEmpty()){
            fragment.getActivity().runOnUiThread(() -> recipeListLayout.setBackground(nothingHereYetBackground));
        } else{
            for (Recipe r : recipes){
                recipe_id.add(r.getId());
                recipe_name.add(r.getRecipeName());
                recipe_ingredients.add(r.getIngredients());
                recipe_directions.add(r.getDirections());
            }
        }
        fragment.getActivity().runOnUiThread(() -> notifyDataSetChanged());
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

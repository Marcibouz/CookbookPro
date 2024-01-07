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
        RecyclerView.Adapter<RecipeRecyclerViewAdapter.MyViewHolder>{
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recipe_name_txt.setText(String.valueOf(recipe_name.get(position)));
        RowElementListener rowElementListener =
                new RowElementListener(String.valueOf(recipe_id.get(position)),
                        String.valueOf(recipe_name.get(position)),
                        String.valueOf(recipe_ingredients.get(position)),
                        String.valueOf(recipe_directions.get(position)),
                        fragment);
        holder.rowLayout.setOnClickListener(rowElementListener);
    }

    @Override
    public int getItemCount() {
        return recipe_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView recipe_name_txt;
        LinearLayout rowLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_name_txt = itemView.findViewById(R.id.recipe_name_txt);
            rowLayout = itemView.findViewById(R.id.rowLayout);
        }
    }
}

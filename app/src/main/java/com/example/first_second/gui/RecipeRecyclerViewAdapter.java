package com.example.first_second.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_second.R;

import java.util.LinkedList;

public class RecipeRecyclerViewAdapter extends
        RecyclerView.Adapter<RecipeRecyclerViewAdapter.MyViewHolder>{
    private Context context;
    private LinkedList<String> recipe_id, recipe_name;
    public RecipeRecyclerViewAdapter(Context context, LinkedList<String> recipe_id,
                                     LinkedList<String> recipe_name){
        this.context = context;
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
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
        holder.recipe_id_txt.setText(String.valueOf(recipe_id.get(position)));
        holder.recipe_name_txt.setText(String.valueOf(recipe_name.get(position)));
    }

    @Override
    public int getItemCount() {
        return recipe_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView recipe_id_txt, recipe_name_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_id_txt = itemView.findViewById(R.id.recipe_id_txt);
            recipe_name_txt = itemView.findViewById(R.id.recipe_name_txt);
        }
    }
}

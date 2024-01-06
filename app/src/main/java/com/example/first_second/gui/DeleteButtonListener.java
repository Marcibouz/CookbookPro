package com.example.first_second.gui;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.first_second.R;
import com.example.first_second.local_memory.DatabaseHelper;

public class DeleteButtonListener implements View.OnClickListener{
    private Context context;
    private Fragment fragment;
    private String id;
    public DeleteButtonListener(Context context, Fragment fragment, String id){
        this.context = context;
        this.fragment = fragment;
        this.id = id;
    }
    @Override
    public void onClick(View view) {
        DatabaseHelper db = new DatabaseHelper(context);
        long deleteFeedback = db.deleteOneRecipe(id);

        if(deleteFeedback == -1){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }else{
            NavHostFragment.findNavController(fragment).
                    navigate(R.id.RecipeScreen_to_RecipeListScreen);
            Toast.makeText(context, "Recipe deleted!", Toast.LENGTH_SHORT).show();

            //Actionbar Title setzen
            ActionBar actionBar = ((AppCompatActivity)fragment.getActivity()).getSupportActionBar();
            actionBar.setTitle("Cookbook Pro");
        }
    }
}

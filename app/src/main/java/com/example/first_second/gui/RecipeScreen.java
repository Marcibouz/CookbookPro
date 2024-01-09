package com.example.first_second.gui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.first_second.R;
import com.example.first_second.application_logic.ApplicationLogic;
import com.example.first_second.application_logic.PortionCalculator;
import com.example.first_second.bluetooth.BluetoothHelper;
import com.example.first_second.databinding.RecipescreenBinding;
import com.example.first_second.local_memory.DatabaseHelper;
import com.example.first_second.local_memory.LocalMemory;


public class RecipeScreen extends Fragment {
    private RecipescreenBinding binding;
    private Context context;
    private Activity activity;
    private EditText recipe_name_input, ingredients_input, directions_input, multiplier;
    private Button update_button, delete_button, calculate_button, share_button;
    private String id, recipe_name, ingredients, directions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RecipescreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        activity = this.getActivity();

        recipe_name_input = view.findViewById(R.id.recipe_name2);
        ingredients_input = view.findViewById(R.id.ingredients2);
        directions_input = view.findViewById(R.id.directions2);
        multiplier = view.findViewById(R.id.multiplier);
        update_button = view.findViewById(R.id.update_button);
        delete_button = view.findViewById(R.id.delete_button);
        calculate_button = view.findViewById(R.id.calculate_button);
        share_button = view.findViewById(R.id.share_button);

        getAndSetArgData();

        //setOnclicklistener für update button
        UpdateButtonListener updateButtonListener = new UpdateButtonListener();
        update_button.setOnClickListener(updateButtonListener);

        //setOnclicklistener für delete button
        DeleteButtonListener deleteButtonListener = new DeleteButtonListener();
        delete_button.setOnClickListener(deleteButtonListener);

        //setOnclicklistener für calculate button
        CalculateButtonListener calculateButtonListener = new CalculateButtonListener();
        calculate_button.setOnClickListener(calculateButtonListener);

        //setOnclicklistener für share button
        ShareButtonListener shareButtonListener = new ShareButtonListener();
        share_button.setOnClickListener(shareButtonListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getAndSetArgData(){
        //Getting Data from Args
        id = RecipeScreenArgs.fromBundle(getArguments()).getId();
        recipe_name = RecipeScreenArgs.fromBundle(getArguments()).getRecipeName();
        ingredients = RecipeScreenArgs.fromBundle(getArguments()).getIngredients();
        directions = RecipeScreenArgs.fromBundle(getArguments()).getDirections();

        //Setting Edittexts
        recipe_name_input.setText(recipe_name);
        ingredients_input.setText(ingredients);
        directions_input.setText(directions);
    }

    private class UpdateButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            LocalMemory lm = new DatabaseHelper(context);
            String recipe_name = recipe_name_input.getText().toString().trim();
            String ingredients = ingredients_input.getText().toString().trim();
            String directions = directions_input.getText().toString().trim();
            int addFeedback = lm.updateRecipe(id, recipe_name, ingredients, directions);

            if(addFeedback == 0){
                Toast.makeText(context, "Recipe was deleted!", Toast.LENGTH_SHORT).show();
            }else{
                NavHostFragment.findNavController(RecipeScreen.this).
                        navigate(R.id.RecipeScreen_to_RecipeListScreen);
                //Actionbar Title setzen
                ActionBar actionBar = ((AppCompatActivity)RecipeScreen.this.getActivity()).
                        getSupportActionBar();
                actionBar.setTitle("Cookbook Pro");

                Toast.makeText(context, "Recipe updated!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DeleteButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            LocalMemory lm = new DatabaseHelper(context);
            int deleteFeedback = lm.deleteOneRecipe(id);

            if(deleteFeedback == 0){
                Toast.makeText(context, "Recipe is already deleted!", Toast.LENGTH_SHORT).show();
            }else{
                NavHostFragment.findNavController(RecipeScreen.this).
                        navigate(R.id.RecipeScreen_to_RecipeListScreen);
                //Actionbar Title setzen
                ActionBar actionBar = ((AppCompatActivity)RecipeScreen.this.getActivity()).
                        getSupportActionBar();
                actionBar.setTitle("Cookbook Pro");

                Toast.makeText(context, "Recipe deleted!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CalculateButtonListener implements View.OnClickListener{
        private final String ingredientsToScale = ingredients_input.getText().toString();
        @Override
        public void onClick(View view) {
            String multiplierString = multiplier.getText().toString();
            double multiplierDouble;
            if (multiplierString.isEmpty()){
                multiplierDouble = 1;
            } else {
                multiplierDouble = Double.parseDouble(multiplierString);
            }
            ApplicationLogic applicationlogic = new PortionCalculator();
            String result = applicationlogic.calculatePortion(ingredientsToScale, multiplierDouble);
            ingredients_input.setText(result);
        }
    }

    private class ShareButtonListener implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.S)
        @Override
        public void onClick(View view) {
            BluetoothHelper bluetoothHelper = new BluetoothHelper(context, activity);
            if (bluetoothHelper.checkPermissions()) { // Permission check
                if (bluetoothHelper.activateBluetooth()) {
                    NavHostFragment.findNavController(RecipeScreen.this).
                            navigate(R.id.RecipeScreen_to_AvailableDevicesScreen);
                    ActionBar actionBar = ((AppCompatActivity)RecipeScreen.this.getActivity()).
                            getSupportActionBar();
                    actionBar.setTitle("Cookbook Pro");
                } else {
                    Toast.makeText(context, "This device does not have Bluetooth Compatibility!", Toast.LENGTH_SHORT).show();
                }
            } else { // Notify User that Permission are missing
                Toast.makeText(context, "Insufficient Permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
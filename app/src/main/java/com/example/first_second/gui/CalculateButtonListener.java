package com.example.first_second.gui;

import android.view.View;
import android.widget.EditText;

import com.example.first_second.application_logic.ApplicationLogic;

public class CalculateButtonListener implements View.OnClickListener{
    private EditText ingredients, multiplier;
    private String ingredientsToScale;
    public CalculateButtonListener(EditText ingredients, EditText multiplier,
                                   String ingredientsToScale){
        this.ingredients = ingredients;
        this.multiplier = multiplier;
        this.ingredientsToScale = ingredientsToScale;
    }
    @Override
    public void onClick(View view) {
        String multiplierString = multiplier.getText().toString();
        double multiplierDouble;
        if (multiplierString.isEmpty()){
            multiplierDouble = 1;
        } else {
            multiplierDouble = Double.parseDouble(multiplierString);
        }
        String result = ApplicationLogic.calculatePortion(ingredientsToScale, multiplierDouble);
        ingredients.setText(result);
    }
}

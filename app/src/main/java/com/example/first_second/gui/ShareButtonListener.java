package com.example.first_second.gui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.first_second.bluetooth.BluetoothHelper;

public class ShareButtonListener implements View.OnClickListener {
    private Context context;
    private EditText recipe_name, ingredients, directions;
    private Fragment fragment;
    private Activity activity;
    public ShareButtonListener(Context context, Fragment fragment, Activity activity, EditText recipe_name, EditText ingredients,
                               EditText directions){
        this.context = context;
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
        this.directions = directions;
        this.fragment = fragment;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        BluetoothHelper bluetoothHelper = new BluetoothHelper(context, activity);
        if (bluetoothHelper.activateBluetooth()) { // Only proceed if Bluetooth is supported and permission has been granted
            bluetoothHelper.findDevices();
        }
    }
}

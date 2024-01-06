package com.example.first_second.gui;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.first_second.bluetooth.BluetoothHelper;

public class ReceiveButtonListener implements View.OnClickListener {
    private Context context;
    private EditText recipe_name, ingredients, directions;
    private Fragment fragment;
    public ReceiveButtonListener(Context context,  Fragment fragment, EditText recipe_name, EditText ingredients,
                               EditText directions){
        this.context = context;
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
        this.directions = directions;
        this.fragment = fragment;
    }

    @Override
    public void onClick(View view) {
        BluetoothHelper bluetoothHelper = new BluetoothHelper(context);
        bluetoothHelper.startDiscoverable(); // Sorgt dafür, dass das Gerät automatisch Bluetooth aktiviert. Ein expliziter Aufruf von activateBluetooth() ist daher nicht notwendig.
    }
}

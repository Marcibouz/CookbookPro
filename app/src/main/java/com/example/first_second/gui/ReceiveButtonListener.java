package com.example.first_second.gui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.first_second.bluetooth.BluetoothHelper;

public class ReceiveButtonListener implements View.OnClickListener {
    private Context context;
    private EditText recipe_name, ingredients, directions;
    private Activity activity;
    public ReceiveButtonListener(){

    }

    @Override
    public void onClick(View view) {
        BluetoothHelper bluetoothHelper = new BluetoothHelper(context, activity);
        bluetoothHelper.startDiscoverable(); // Sorgt dafür, dass das Gerät automatisch Bluetooth aktiviert. Ein expliziter Aufruf von activateBluetooth() ist daher nicht notwendig.
    }
}

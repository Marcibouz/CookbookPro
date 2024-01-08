package com.example.first_second.gui;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.first_second.R;
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

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public void onClick(View view) {
        BluetoothHelper bluetoothHelper = new BluetoothHelper(context, activity);
        if (bluetoothHelper.checkPermissions()) { // Permission check
            if (bluetoothHelper.activateBluetooth()) {
                NavHostFragment.findNavController(fragment).
                        navigate(R.id.RecipeScreen_to_AvailableDevicesScreen);
            } else {
                Toast.makeText(context, "This device does not have Bluetooth Compatibility!", Toast.LENGTH_SHORT).show();
            }
        } else { // Notify User that Permission are missing
            Toast.makeText(context, "Insufficient Permissions", Toast.LENGTH_SHORT).show();
        }
    }
}

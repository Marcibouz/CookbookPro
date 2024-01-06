package com.example.first_second.bluetooth;

import android.Manifest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.Set;

public class BluetoothHelper extends Activity {
    private static final String TAG = "BluetoothHelper";

    //Intent request Codes
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Context context;

    public BluetoothHelper(Context context) {
        this.context = context;
    }

    public void activateBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "This device does not have Bluetooth Compatibility!", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Device does not support Bluetooth.");
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Log.d(TAG, "Not Enabled");
                if (!checkBluetoothPermission()) {
                    return;
                }
                bluetoothAdapter.enable();
            } else {
                Log.d(TAG, "BluetoothAdapter already Enabled.");
            }
        }
    }

    private void requestBluetoothPermission() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_ENABLE_BT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void findDevices() {
        if (!checkBluetoothPermission()) {
            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
            }
        }
    }

    public void startDiscoverable() {
        int requestCode = 1;
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        if (!checkBluetoothPermission()) {
            return;
        }
        startActivityForResult(discoverableIntent, requestCode); // Gerät bleibt discoverable für die Standardlänge von 2 Minuten.
    }

    private boolean checkBluetoothPermission() {
        if (ActivityCompat.checkSelfPermission((Activity) context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permissions");
            Log.d(TAG, "Asking for permission");
            requestBluetoothPermission();
            if (ActivityCompat.checkSelfPermission((Activity) context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Failed to activate Bluetooth: Permission denied.", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Permission denied. Cancelling Bluetooth");
                return false;
            }
        }
        Log.d(TAG, "Connect Permission granted");
        return true;
    }
}

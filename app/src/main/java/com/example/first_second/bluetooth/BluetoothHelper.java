package com.example.first_second.bluetooth;

import android.Manifest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BluetoothHelper extends Activity {
    private static final String TAG = "BluetoothHelper";

    //Intent request Codes
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_ADVERTISE_BT = 2;
    private static final int REQUEST_SCAN_BT = 3;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Context context;
    private Activity activity;
    //Maps consisting of MAC-Address and Name of available devices.
    private Map<String, String> availableBondedDevices = new LinkedHashMap<>();
    private Map<String, String> availableDevices = new LinkedHashMap<>();


    public BluetoothHelper(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public BluetoothHelper() {

    }

    public boolean activateBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "This device does not have Bluetooth Compatibility!", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Device does not support Bluetooth.");
            return false;
        } else {
            if (!checkBluetoothPermission()) {
                return false;
            }
            if (!bluetoothAdapter.isEnabled()) {
                Log.d(TAG, "Not Enabled");
                bluetoothAdapter.enable();
            } else {
                Log.d(TAG, "BluetoothAdapter already Enabled.");
            }
        }
        return true;
    }

    private void requestBluetoothPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_ENABLE_BT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_ENABLE_BT || requestCode == REQUEST_ADVERTISE_BT || requestCode == REQUEST_SCAN_BT) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean checkBluetoothPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No permissions");
            Log.d(TAG, "Asking for permission");
            requestBluetoothPermission();
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Failed to activate Bluetooth: Permission denied.", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Permission denied. Cancelling Bluetooth");
                return false;
            }
        }
        Log.d(TAG, "Connect Permission granted");
        return true;
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
                availableBondedDevices.put(deviceHardwareAddress, deviceName);
            }
        }
        bluetoothAdapter.startDiscovery();
    }

    public void startDiscoverable() {
        int requestCode = 1;
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        if (!checkBluetoothPermission()) {
            return;
        }
        startActivityForResult(discoverableIntent, requestCode); // Gerät bleibt discoverable für die Standardlänge von 2 Minuten.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                checkBluetoothPermission();
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                availableDevices.put(deviceHardwareAddress, deviceName);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, availableBondedDevices.toString());
        unregisterReceiver(receiver); // Closing receiver afterwards to free resources
    }
}




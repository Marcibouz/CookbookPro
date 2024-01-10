package com.example.first_second.bluetooth;

import android.Manifest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.first_second.gui.BluetoothObserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BluetoothHelper extends AppCompatActivity {
    private static final String TAG = "BluetoothHelper"; // Tag for Logging
    //Permission Strings
    public static final String[] SHARE_PERMISSIONS = { // Required Permissions for sharing Recipes
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN};
    public static final String[] RECEIVE_PERMISSIONS = { // Required Permissions for sharing Recipes
            Manifest.permission.BLUETOOTH_ADVERTISE};
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Context context;
    private Activity activity;
    //Maps consisting of MAC-Address and Name of available devices.
    private Map<BluetoothDevice, String> availableBondedDevices = new LinkedHashMap<>();
    private Map<BluetoothDevice, String> availableDevices = new LinkedHashMap<>();
    private List<BluetoothObserver> observers = new LinkedList<>();
    private static final String APP_NAME = "CookBook Pro";
    private static final UUID UNIQUE_ID = UUID.randomUUID();



    public BluetoothHelper(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public BluetoothHelper() {

    }

    public Map<BluetoothDevice, String> getAvailableBondedDevices() {
        return availableBondedDevices;
    }

    public Map<BluetoothDevice, String> getAvailableDevices() {
        return availableDevices;
    }

    public void addBluetoothObserver(BluetoothObserver bluetoothObserver) {
        observers.add(bluetoothObserver);
    }

    public void notifyObservers() {
        for (BluetoothObserver bo : observers) {
            bo.propertyChange(this);
        }
    }

    public boolean checkPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            // Before API Level 31, these Permissions did not exist
            for (String s : SHARE_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(context, s)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean activateBluetooth() {
        if (bluetoothAdapter == null) {
            Log.d(TAG, "Device does not support Bluetooth.");
            return false;
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Log.d(TAG, "Not Enabled");
                return false;
            } else {
                Log.d(TAG, "BluetoothAdapter already Enabled.");
            }
        }
        return true;
    }


    public void findDevices() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            Log.d(TAG, "Paired devices exist.");
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                availableBondedDevices.put(device, deviceName);
                notifyObservers();
            }
        }
        startDiscovery();
    }

    private void startDiscovery() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        if (bluetoothAdapter.isDiscovering()) { // Cancel ongoing discovery
            bluetoothAdapter.cancelDiscovery();
        }
        if(!availableDevices.isEmpty()){ // Empty list from previous search
            availableDevices.clear();
        }
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(receiver, filter);

        boolean discoveryStarted = bluetoothAdapter.startDiscovery();
        if(!discoveryStarted) {
            Toast.makeText(context,
                    "Discovery not started. Fine Location access must be Granted",
                    Toast.LENGTH_SHORT).show();
        }
        Log.d("Discovery Status",
                discoveryStarted ? "Started Successfully" : "Failed to Start");

    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                Log.d(TAG, "Found a device");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                if (deviceName != null) { // Exclude devices with no name
                    availableDevices.put(device, deviceName);
                }
                notifyObservers();
            }
        }
    };

    public void startDiscoverable() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_ADVERTISE)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        context.startActivity(discoverableIntent);
        Toast.makeText(context, "Discoverable läuft durch", Toast.LENGTH_SHORT).show();

        BluetoothServerThread bluetoothServerThread =
                new BluetoothServerThread(bluetoothAdapter, APP_NAME, UNIQUE_ID);
        bluetoothServerThread.start();

    }

    public void createClientThread(BluetoothDevice device) {
        BluetoothClientThread bluetoothClientThread = new BluetoothClientThread(bluetoothAdapter, device, UNIQUE_ID);
        bluetoothClientThread.start();

    }
}
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
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.first_second.gui.BluetoothObserver;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiresApi(api = Build.VERSION_CODES.S)
public class BluetoothHelper extends AppCompatActivity {
    private static final String TAG = "BluetoothHelper"; // Tag for Logging
    //Permission Strings
    public static final String[] SHARE_PERMISSIONS = { // Required Permissions for sharing Recipes
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN};
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Context context;
    private Activity activity;
    //Maps consisting of MAC-Address and Name of available devices.
    private Map<String, String> availableBondedDevices = new LinkedHashMap<>();
    private Map<String, String> availableDevices = new LinkedHashMap<>();
    private List<BluetoothObserver> observers = new LinkedList<>();


    public BluetoothHelper(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public BluetoothHelper() {

    }

    public Map<String, String> getAvailableBondedDevices() {
        return availableBondedDevices;
    }

    public Map<String, String> getAvailableDevices() {
        return availableDevices;
    }
    public void addBluetoothObserver(BluetoothObserver bluetoothObserver){
        observers.add(bluetoothObserver);
    }
    public void notifyObservers(){
        for (BluetoothObserver bo : observers){
            bo.propertyChange(this);
        }
    }

    public boolean checkPermissions() {
        for (String s : SHARE_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED) {
                return false;
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

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            Log.d(TAG, "Paired devices exist.");
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                availableBondedDevices.put(deviceHardwareAddress, deviceName);
                notifyObservers();
            }
        }
        startDiscovery();
    }

    private void startDiscovery() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                Log.d(TAG, "Found a device");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                availableDevices.put(deviceHardwareAddress, deviceName);
                notifyObservers();
            }
        }
    };
}
package com.example.first_second.bluetooth;

import android.Manifest;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.first_second.gui.Gui;
import com.example.first_second.gui.MainActivity;
import com.example.first_second.memory.Recipe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BluetoothImpl extends AppCompatActivity implements Bluetooth {
    private static final String TAG = "Bluetooth"; // Tag for Logging
    //Permission Strings
    public static final String[] SHARE_PERMISSIONS = { // Required Permissions for sharing Recipes
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN};
    public static final String[] RECEIVE_PERMISSIONS = { // Required Permissions for sharing Recipes
            Manifest.permission.BLUETOOTH_ADVERTISE};
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Context context;
    private MainActivity activity;
    private Gui gui;
    //Maps consisting of MAC-Address and Name of available devices.
    private Map<BluetoothDevice, String> availableBondedDevices = new LinkedHashMap<>();
    private Map<BluetoothDevice, String> availableDevices = new LinkedHashMap<>();
    private List<BluetoothObserver> observers = new LinkedList<>();
    private static final String APP_NAME = "CookBook Pro";
    private static final UUID UNIQUE_ID = UUID.fromString("b7adcd2b-9256-48d6-a7b6-922e95d91ce1");


    public BluetoothImpl(Context context, MainActivity activity) {
        this.context = context;
        this.activity = activity;
        this.gui = activity;
    }

    public BluetoothImpl() {

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

    private void notifyObserversBoundedDeviceAdded() {
        for (BluetoothObserver bo : observers) {
            bo.bondedDeviceAdded(this);
        }
    }

    private void notifyObserversAvailableDeviceAdded() {
        for (BluetoothObserver bo : observers) {
            bo.availableDeviceAdded(this);
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

    @SuppressLint("MissingPermission")
    public boolean activateBluetooth() {
        if (bluetoothAdapter == null) {
            Log.d(TAG, "Device does not support Bluetooth.");
            return false;
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Log.d(TAG, "Bluetooth Adapter Not Enabled, enabling Adapter");
                bluetoothAdapter.enable();
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
                notifyObserversBoundedDeviceAdded();
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
        if (!availableDevices.isEmpty()) { // Empty list from previous search
            availableDevices.clear();
        }
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(receiver, filter);

        boolean discoveryStarted = bluetoothAdapter.startDiscovery();
        if (!discoveryStarted) {
            gui.showToast("Discovery not started. Fine Location access must be Granted!");
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
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                if (deviceName != null) { // Exclude devices with no name
                    Log.d(TAG, "Found a device");
                    availableDevices.put(device, deviceName);
                }
                notifyObserversAvailableDeviceAdded();
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

        createServerThread(bluetoothAdapter, APP_NAME, UNIQUE_ID, context);
    }

    public void createClientThread(BluetoothDevice device, Recipe recipe) {
        BluetoothClientThread bluetoothClientThread = new BluetoothClientThread
                (bluetoothAdapter, device, UNIQUE_ID, serializeRecipe(recipe), gui);
        bluetoothClientThread.start();
    }

    public void createServerThread(
            BluetoothAdapter adapter, String appName, UUID uuid, Context context) {
        BluetoothServerThread bluetoothServerThread =
                new BluetoothServerThread(adapter, appName, uuid, context, gui);
        bluetoothServerThread.start();
    }

    public byte[] serializeRecipe(Recipe recipe) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            if (recipe == null) {
                return null;
            }
            objectOutputStream.writeObject(recipe);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Recipe deserializeRecipe(byte[] serializedRecipe) {
        Log.d(TAG, Arrays.toString(serializedRecipe));
        if (serializedRecipe == null) {
            return null;
        }
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedRecipe);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

            return (Recipe) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO Refactor so that static method is no longer necessary, this is ugly
    public static Recipe deserializeIncomingRecipe(byte[] serializedRecipe) {
        Log.d(TAG, Arrays.toString(serializedRecipe));
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedRecipe);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

            return (Recipe) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
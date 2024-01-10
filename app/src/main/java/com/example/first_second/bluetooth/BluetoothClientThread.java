package com.example.first_second.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothClientThread extends Thread {
    private BluetoothSocket socket;
    private BluetoothDevice device;
    private BluetoothAdapter adapter;
    private String recipeName;
    private String recipeIngredients;
    private String recipeInstructions;
    private static final String TAG = "ConnectThread";

    @SuppressLint("MissingPermission")
    public BluetoothClientThread(BluetoothAdapter adapter, BluetoothDevice device, UUID uuid) {
        this.device = device;
        this.adapter = adapter;

        try {
            socket = device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
    }

    @SuppressLint("MissingPermission")
    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        adapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket.
            socket.connect();
            //We create the bluetooth connected thread, that represents the active connection
            BluetoothActiveThread bluetoothActiveThread = new BluetoothActiveThread(socket);

            //Start this thread
            bluetoothActiveThread.start();
            bluetoothActiveThread.write(recipeName.getBytes());
            bluetoothActiveThread.write(recipeIngredients.getBytes());
            bluetoothActiveThread.write(recipeInstructions.getBytes());
            bluetoothActiveThread.cancel();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                socket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        BluetoothActiveThread bluetoothActiveThread = new BluetoothActiveThread(socket);
        bluetoothActiveThread.start();
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }

}

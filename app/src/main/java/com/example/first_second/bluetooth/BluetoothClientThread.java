package com.example.first_second.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class BluetoothClientThread extends Thread {
    private BluetoothSocket socket;
    private BluetoothDevice device;
    private BluetoothAdapter adapter;
    private String recipeName = "name";
    private String recipeIngredients = "zutaten";
    private String recipeInstructions = "anweisungen";
    private static final String TAG = "ClientThread";

    @SuppressLint("MissingPermission")
    public BluetoothClientThread(BluetoothAdapter adapter, BluetoothDevice device, UUID uuid) {
        Log.d(TAG, "Client Thread created");
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
        Log.d(TAG, "Client Thread running");

        // Cancel discovery because it otherwise slows down the connection.
        adapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket.
            socket.connect();
            // Create the bluetooth connected thread that represents the active connection
            BluetoothActiveThread bluetoothActiveThread = new BluetoothActiveThread(socket);

            //Start this thread
            bluetoothActiveThread.start();

        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                socket.close();
                Log.d(TAG, "Client Socket Closed");
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        BluetoothActiveThread bluetoothActiveThread = new BluetoothActiveThread(socket);
        bluetoothActiveThread.start();
        bluetoothActiveThread.write(recipeName.getBytes());
        bluetoothActiveThread.write(recipeIngredients.getBytes());
        bluetoothActiveThread.write(recipeInstructions.getBytes());
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
package com.example.first_second.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.example.first_second.gui.MainActivity;
import com.example.first_second.local_memory.Recipe;

import java.io.IOException;
import java.util.UUID;

public class BluetoothClientThread extends Thread {
    private MainActivity activity;
    private BluetoothSocket socket;
    private BluetoothDevice device;
    private BluetoothAdapter adapter;
    private String recipeName;
    private String recipeIngredients;
    private String recipeInstructions;
    private static final String TAG = "ClientThread";

    @SuppressLint("MissingPermission")
    public BluetoothClientThread(BluetoothAdapter adapter, BluetoothDevice device, UUID uuid, String name, String ingredients, String instructions, MainActivity activity) {
        Log.d(TAG, "Client Thread created");
        this.device = device;
        this.adapter = adapter;
        this.recipeName = name;
        this.recipeIngredients = ingredients;
        this.recipeInstructions = instructions;
        this.activity = activity;

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

        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                socket.close();
                Log.d(TAG, "Client Socket Closed");
                activity.showToast("Connection failed!");
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated withs
        // the connection in a separate thread.
        // Create the bluetooth connected thread that represents the active connection
        BluetoothActiveThread bluetoothActiveThread = new BluetoothActiveThread(socket, activity);

        //Start this thread
        bluetoothActiveThread.start();

        // Write recipe data
        bluetoothActiveThread.write(new Recipe(recipeName, recipeIngredients, recipeInstructions));

        // Closes ClientThread
        cancel();
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

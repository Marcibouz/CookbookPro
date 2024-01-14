package com.example.first_second.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.example.first_second.gui.Gui;
import com.example.first_second.gui.MainActivity;
import com.example.first_second.memory.Memory;
import com.example.first_second.memory.MemoryImpl;
import com.example.first_second.memory.Recipe;

import java.io.IOException;
import java.util.UUID;

public class BluetoothServerThread extends Thread {
    private Gui gui;
    private Context context;
    private static final String TAG = "ServerThread";
    private BluetoothServerSocket serverSocket;

    @SuppressLint("MissingPermission")
    public BluetoothServerThread(BluetoothAdapter adapter, String name, UUID uuid, Context context,
                                 Gui gui) {
        Log.d(TAG, "Server Thread created");
        this.context = context;
        this.gui = gui;

        try {
            serverSocket = adapter.listenUsingRfcommWithServiceRecord(name, uuid);
        } catch (IOException e) {
            Log.e(TAG, "Socket failed to listen for connection", e);
        }
    }

    public void run() {
        Log.d(TAG, "Server Thread running");
        BluetoothSocket socket;
        while(true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                cancel();
                break;
            }

            if (socket != null) { // Connection is being established
                // Create the bluetooth connected thread that represents the active connection
                BluetoothActiveThread bluetoothActiveThread = new BluetoothActiveThread(socket, gui);

                // Start this thread
                bluetoothActiveThread.start();
                Log.d(TAG, "Active Thread Started");

                // Reads Recipe Data
                byte[] serializedRecipe = bluetoothActiveThread.read();
                if (serializedRecipe == null) {
                    gui.showToast("Invalid Recipe received!");
                    Log.d(TAG, "Received serialized recipe was null");
                } else {
                    Recipe recipe = BluetoothImpl.deserializeIncomingRecipe(serializedRecipe);
                    Memory memory = MemoryImpl.getMemoryImpl(context);
                    memory.addRecipe(recipe);
                    gui.showToast("Received Recipe!");
                }
            }
        }
    }

    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the server socket", e);
        }
    }
}

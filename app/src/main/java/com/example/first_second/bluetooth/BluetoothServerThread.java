package com.example.first_second.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothServerThread extends Thread {
    private Context context;
    private static final String TAG = "ServerThread";
    private BluetoothServerSocket serverSocket;

    @SuppressLint("MissingPermission")
    public BluetoothServerThread(BluetoothAdapter adapter, String name, UUID uuid, Context context) {
        Log.d(TAG, "Server Thread created");
        this.context = context;

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
                break;
            }

            if (socket != null) { // Connection is being established
                // Create the bluetooth connected thread that represents the active connection
                BluetoothActiveThread bluetoothActiveThread = new BluetoothActiveThread(socket);

                // Start this thread
                bluetoothActiveThread.start();

                // Reads Recipe Data and saves it in data bank
                bluetoothActiveThread.read(context);

                // Closes ServerThread
                cancel();
                try {
                    serverSocket.close();
                    break;
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close server socket", e);
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

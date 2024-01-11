package com.example.first_second.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BluetoothActiveThread extends Thread {
    private BluetoothSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private String recipeName;
    private String recipeIngredients;
    private String recipeInstructions;
    private byte[] buffer = new byte[1024]; // buffer store for the stream
    private static final String TAG = "ActiveThread";

    public BluetoothActiveThread(BluetoothSocket socket) {
        this.socket = socket;

        // Get the input and output streams
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);
        }
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
        }
    }

    public void run() {
        Log.d(TAG, "Active Thread Running");
        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                // Read from the InputStream.
                inputStream.read(buffer);
            } catch (IOException e) {
                Log.d(TAG, "Input stream was disconnected", e);
                break;
            }
        }
    }

    public void read() {
        int bytesRead;

        try {
            while (true) {
                bytesRead = inputStream.read(buffer);

                if (bytesRead == -1) {
                    // End of stream reached
                    break;
                }

                // Process the received data
                processReceivedData(Arrays.copyOf(buffer, bytesRead));
            }
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when reading data", e);
        }
    }

    private void processReceivedData(byte[] data) {

        String receivedData = new String(data, StandardCharsets.UTF_8);

        // Do something with the received data
        Log.d(TAG, "Received data: " + receivedData);
    }


    // send data to the remote device.
    public void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when sending data", e);
        }
    }

    // shuts down the connection.
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}


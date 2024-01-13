package com.example.first_second.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.example.first_second.gui.Gui;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class BluetoothActiveThread extends Thread {
    private Gui gui;
    private BluetoothSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private static final String TAG = "ActiveThread";

    public BluetoothActiveThread(BluetoothSocket socket, Gui gui) {
        this.socket = socket;
        this.gui = gui;

        // Get the input and output streams
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating output stream", e);
        }
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when creating input stream", e);
        }
        Log.d(TAG, "Active Thread Running");
    }

    protected byte[] read() {
        Log.d(TAG, "Read Method Called");
        try {
            // Reads all Bytes from Input Stream
            Log.d(TAG, "try-block entered");
            byte[] bytes = new byte[inputStream.available()];
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            dataInputStream.readFully(bytes);
            Log.d(TAG, "Returning recipe Data");
            return bytes;
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when reading data", e);
            return null;
        } finally {
            // closes ActiveThread after read() either failed or succeeded
            cancel();
        }
    }

    // send data to the remote device.
    protected void write(byte[] recipe) {
        Log.d(TAG, "Write Method Called");
        try {
            outputStream.write(recipe);
            gui.showToast("Recipe sent successfully");
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


package com.example.first_second.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.example.first_second.local_memory.DatabaseHelper;
import com.example.first_second.local_memory.LocalMemory;
import com.example.first_second.local_memory.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class BluetoothActiveThread extends Thread {
    private BluetoothSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Recipe recipe;
    private byte[] buffer = new byte[1024]; // buffer store for the stream
    private static final String TAG = "ActiveThread";

    public BluetoothActiveThread(BluetoothSocket socket) {
        this.socket = socket;

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
    }

    public void read(Context context) {
        Log.d(TAG, "Read Method Called");
        try {
            objectInputStream = new ObjectInputStream(inputStream);
            recipe = (Recipe) objectInputStream.readObject();
            LocalMemory lm = new DatabaseHelper(context);
            lm.addRecipe(recipe);
            Log.d(TAG, "Recipe: " + recipe.toString());
        } catch (IOException e) {
            Log.e(TAG, "Error occurred when reading data", e);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Class could not be found", e);
        } finally {
            // closes ActiveThread after read() either failed or succeeded
            cancel();
        }
    }

    // send data to the remote device.
    public void write(Recipe recipe) {
        Log.d(TAG, "Write Method Called");
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(recipe);
            objectOutputStream.flush();
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


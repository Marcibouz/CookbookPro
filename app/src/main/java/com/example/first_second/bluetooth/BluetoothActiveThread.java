package com.example.first_second.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.example.first_second.gui.Gui;
import com.example.first_second.memory.MemoryImpl;
import com.example.first_second.memory.Memory;
import com.example.first_second.memory.Recipe;

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
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Recipe recipe;
    private byte[] buffer = new byte[1024]; // buffer store for the stream
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
    }

    protected void read(Context context) {
        Log.d(TAG, "Read Method Called");
        try {
            objectInputStream = new ObjectInputStream(inputStream);
            recipe = (Recipe) objectInputStream.readObject();
            Memory memory = MemoryImpl.getMemoryImpl(context);
            memory.addRecipe(recipe);
            Log.d(TAG, "Recipe: " + recipe.toString());
            gui.showToast("Recipe received!");
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
    protected void write(Recipe recipe) {
        Log.d(TAG, "Write Method Called");
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(recipe);
            objectOutputStream.flush();
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


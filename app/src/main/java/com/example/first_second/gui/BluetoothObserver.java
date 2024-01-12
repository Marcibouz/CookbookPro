package com.example.first_second.gui;

import com.example.first_second.bluetooth.BluetoothImpl;

public interface BluetoothObserver {
    void bondedDeviceAdded(BluetoothImpl bluetooth);
    void availableDeviceAdded(BluetoothImpl bluetooth);
}

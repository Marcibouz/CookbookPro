package com.example.first_second.bluetooth;

import com.example.first_second.bluetooth.BluetoothImpl;

public interface BluetoothObserver {
    void bondedDeviceAdded(BluetoothImpl bluetooth);
    void availableDeviceAdded(BluetoothImpl bluetooth);
}

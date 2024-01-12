package com.example.first_second.gui;

import com.example.first_second.bluetooth.BluetoothHelperImpl;

public interface BluetoothObserver {
    void bondedDeviceAdded(BluetoothHelperImpl bluetoothHelper);
    void availableDeviceAdded(BluetoothHelperImpl bluetoothHelper);
}

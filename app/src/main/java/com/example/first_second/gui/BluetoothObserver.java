package com.example.first_second.gui;

import com.example.first_second.bluetooth.BluetoothHelper;

public interface BluetoothObserver {
    void bondedDeviceAdded(BluetoothHelper bluetoothHelper);
    void availableDeviceAdded(BluetoothHelper bluetoothHelper);
}

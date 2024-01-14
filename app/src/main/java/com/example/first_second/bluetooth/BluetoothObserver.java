package com.example.first_second.bluetooth;

public interface BluetoothObserver {
    /**
     * Methode um alle registrierten Observer der Bluetooth-Komponente zu benachrichtigen, wenn
     * ein neues Gerät gekoppelt wird oder ein vorher gekoppeltes Gerät entfernt wird.
     * @param bluetooth Bluetooth-Instanz, in der die Observer registriert sind
     */
    void bondedDeviceAdded(BluetoothImpl bluetooth);

    /**
     * Methode um alle registrierten Observer der Bluetooth-Komponente zu benachrichtigen, wenn
     * ein neues Gerät verfügbar wird oder ein vorher verfügbares Gerät nicht mehr erreichbar ist.
     * @param bluetooth Bluetooth-Instanz, in der die Observer registriert sind
     */
    void availableDeviceAdded(BluetoothImpl bluetooth);
}



package com.example.first_second.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.Map;

/**
 * Schnittstelle für Bluetooth Funktionen
 */
public interface BluetoothHelper {

    /**
     * Gibt eine Map aller bereits verbundener Bluetooth-Geräte zurück
     * @return Map der verbundenen Bluetooth-Geräte
     */
    Map<BluetoothDevice, String> getAvailableBondedDevices();

    /**
     * Gibt eine Map aller verfügbarer Bluetooth-Geräte zurück
     * @return Map der verfügbaren Bluetooth-Geräte
     */
    Map<BluetoothDevice, String> getAvailableDevices();

    /**
     * Methode zum Überprüfen der Berechtigungen des Nutzers.
     * @return boolean, ob Permissions gesetzt sind oder nicht
     */
    boolean checkPermissions();

    /**
     * Aktiviert Bluetooth auf dem Gerät.
     * @return false, wenn das Gerät nicht Bluetooth-fähig ist. true, wenn es Bluetooth-fähig ist
     * und der Bluetooth Adapter aktiviert ist.
     */
    boolean activateBluetooth();

    /**
     * Methode zum Finden von Geräten, mit denen sich die App verbinden kann.
     * Zuerst werden bereits verbundene Geräte aufgelistet
     * Danach wird eine Liste angezeigt, welche alle Geräte in der Umgebung anzeigt,
     * die discoverable sind.
     */
    void findDevices();

    /**
     * Sorgt dafür, dass das Gerät von anderen Geräten gefunden werden kann und eine Verbindung
     * aufgebaut werden kann. Hierfür wird ein neuer ServerThread erstellt, welcher darauf wartet,
     * dass ein ClientThread einen Verbindungsaufbau startet.
     */
    void startDiscoverable();

    /**
     * Erstellt einen neuen ClientThread, welcher versucht, sich mit einem ServerThread zu verbinden.
     * @param device Gerät, mit dem sich verbunden werden soll und an welches die Daten verschickt
     *               werden
     * @param name Name des Rezeptes
     * @param ingredients Zutaten des Rezeptes
     * @param instructions Zubereitungsanweisungen des Rezeptes
     */
    void createClientThread(BluetoothDevice device, String name,
                            String ingredients, String instructions);
}

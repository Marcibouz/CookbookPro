package com.example.first_second.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.example.first_second.gui.Gui;
import com.example.first_second.memory.Recipe;

import java.util.Map;
import java.util.UUID;

/**
 * Schnittstelle für Bluetooth Funktionen
 */
public interface Bluetooth {

    /**
     * Gibt die bereits gekoppelten Geräte des Nutzers in Form einer Map zurück.
     * BluetoothDevice beschreibt das Gerät an sich und enthält Daten wie die MAC-Adresse,
     * während der String der Name des Geräts ist, welches dem Nutzer angezeigt wird.
     * @return Map der verbundenen Bluetooth-Geräte
     */
    Map<BluetoothDevice, String> getAvailableBondedDevices();

    /**
     * Gibt eine Liste aller erreichbaren Bluetooth-Geräte in der Umgebung in Form einer Map zurück.
     * BluetoothDevice beschreibt das Gerät an sich und enthält Daten wie die MAC-Adresse,
     * während der String der Name des Geräts ist, welches dem Nutzer angezeigt wird.
     * @return Map der verfügbaren Bluetooth-Geräte
     */
    Map<BluetoothDevice, String> getAvailableDevices();

    /**
     * Methode zum Überprüfen der benötigten Bluetooth-Berechtigungen des Nutzers.
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
     * Zuerst werden bereits gekoppelte Geräte aufgelistet. Danach wird eine Liste angezeigt,
     * welche alle per Bluetooth erreichbaren Geräte in der Umgebung anzeigt, die discoverable sind.
     */
    void findDevices();

    /**
     * Sorgt dafür, dass das Gerät von anderen Geräten gefunden werden kann und eine Verbindung
     * aufgebaut werden kann. Bei Einwilligung der Nutzers wird hierfür wird ein neuer ServerThread
     * erstellt, welcher darauf wartet, dass ein ClientThread einen Verbindungsaufbau startet.
     */
    void startDiscoverable();

    /**
     * Methode zum Erstellen eines Client Threads. Das Device ist das Zielgerät,
     * mit dem sich über diesen Thread verbunden werden soll. Beim Methodenaufruf wird das
     * Rezept-Objekt, bestehend aus Name, Zutaten und Anweisungen, zu einem Byte-Array serialisiert.
     * @param device Device, an welches die Daten geschickt werden sollen
     * @param recipe Rezept, welches verschickt werden soll
     */
    void createClientThread(BluetoothDevice device, Recipe recipe);

    /**
     * Methode zum Erstellen eines neuen Server Threads, welcher auf die Verbindung eines Clients
     * wartet und ein eintreffende Rezept deserialisiert und abspeichert.
     * @param adapter Bluetooth-Adapter des Geräts
     * @param appName Name der App
     * @param uuid UUID zur Verifikation, dass der Client ebenfalls Instanz der App ist und ein
     *             Rezept versenden möchte.
     * @param context Benötigter Context für die Datenbank.
     */
    void createServerThread(BluetoothAdapter adapter, String appName, UUID uuid, Context context);

    /**
     * Fügt der Bluetooth-Komponente einen Observer hinzu, welcher bei Änderungen der verbundenen
     * und verfügbaren Geräte benachrichtigt wird. Dies dient dazu, um die dem Nutzer angezeigte
     * Liste der gekoppelten und verfügbaren Geräte dynamisch zu aktualisieren.
     * @param bluetoothObserver Observer
     */
    void addBluetoothObserver(BluetoothObserver bluetoothObserver);

    /**
     * Methode zum Serialisieren eines Rezeptes
     * @param recipe Rezept-Objekt, was serialisiert werde soll
     * @return Darstellung des Rezeptes als Byte-Array
     */
    byte[] serializeRecipe(Recipe recipe);

    /**
     * Methode zum Deserialisieren eines Rezeptes
     * @param recipeData Rezeptdaten als Byte-Array, welche wieder als ein Rezept-Objekt dargestellt werden sollen
     * @return Rezept-Objekt aus der recipeData. Returns null if recipeData is null.
     */
    Recipe deserializeRecipe(byte[] recipeData);
}

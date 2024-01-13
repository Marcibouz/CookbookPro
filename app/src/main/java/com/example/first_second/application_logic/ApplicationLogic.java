package com.example.first_second.application_logic;

/**
 * Schnittstelle der ApplicationLogic
 */

public interface ApplicationLogic {
    /**
     * Findet alle Zahlen im gegebenen String (egal ob dezimal oder nicht) und multipliziert diese mit dem gegebenen Wert.
     * Wenn der gegebene Multiplikator kleiner gleich 0 ist, dann wird der String ohne Veränderung zurueckgegeben.
     * Die Zahlen im String werden wie folgt interpretiert:
     * Sie sind entweder Ganzzahlen oder Dezimalzahlen welche nur ein Trennzeichen besitzen duerfen.
     * Dieses ist entweder ein "." oder ",".
     *
     * @param text String dessen Zahlen skaliert werden sollen
     * @param multiplier Multuplikator, mit welchem skaliert werden soll
     * @return String mit skalierten Zahlen oder unveränderter String bei Multiplikator <= 0
     * Wenn der String null ist, wird stattdessen ein leerer String zurückgegeben.
     */
    String calculatePortion(String text, double multiplier);
}

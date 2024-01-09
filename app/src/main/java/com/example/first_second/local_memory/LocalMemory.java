package com.example.first_second.local_memory;

import android.database.Cursor;

/**
 * Schnittstelle des LocalMemory
 */
public interface LocalMemory {

    /**
     * Fügt ein Rezept der Datenbank hinzu.
     *
     * @param recipe_name Name des Rezepts
     * @param ingredients Zutaten des Rezepts
     * @param directions Zubereitung des Rezepts
     * @return ID des neu hinzugefügten Rezepts oder -1 wenn es einen Fehler beim Hinzufügen gab
     */
    long addRecipe(String recipe_name, String ingredients, String directions);

    /**
     * Gibt alle Rezepte der Datenbank zurück.
     *
     * @return Alle Rezepte der Datenbank
     */
    Cursor readAllRecipes();

    /**
     * Aktualisiert das Rezept mit der gegebenenen ID mit dem gegebenen Namen, den Zutaten sowie den
     * Zubereitungsanweisungen.
     *
     * @param id ID des Rezeptes welches aktualisiert werden soll
     * @param recipe_name Neuer Name des Rezepts
     * @param ingredients Neue Zutaten des Rezepts
     * @param directions Neue Zubereitungsanweisungen des Rezepts
     * @return Anzahl der betroffenenen Zeilen
     */
    int updateRecipe(String id, String recipe_name, String ingredients, String directions);

    /**
     * Löscht das Rezept mit der gegebenen ID
     * @param id ID des zu löschenden Rezepts
     *
     * @return Anzahl der betroffenen Zeilen
     */
    int deleteOneRecipe(String id);

    /**
     * Löscht alle Rezepte der Datenbank
     */
    void deleteAllRecipes();
}

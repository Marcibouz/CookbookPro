package com.example.first_second.memory;

import java.util.List;

/**
 * Schnittstelle des Memory
 */
public interface Memory {

    /**
     * Fügt einen Observer hinzu, damit dieser über Veränderungen in der Datenbank benachrichtigt
     * wird.
     *
     * @param memoryObserver Observer welcher benachrichtigt werden soll.
     */
    void addNewDatabaseObserver(MemoryObserver memoryObserver);

    /**
     * Fügt ein Rezept der Datenbank hinzu.
     *
     * @param recipe Das neue Rezept
     * @return ID des neu hinzugefügten Rezepts oder -1 wenn es einen Fehler beim Hinzufügen gab
     */
    long addRecipe(Recipe recipe);

    /**
     * Gibt alle Rezepte der Datenbank zurück.
     *
     * @return Liste mit allen Rezepte der Datenbank, leere Liste wenn die Datenbank leer ist
     */
    List<Recipe> readAllRecipes();

    /**
     * Aktualisiert das Rezept mit der ID mit dem neuen Rezept.
     *
     * @param id ID des zu aktualisierenden Rezepts
     * @param recipe Rezept mit welchem das alte überschrieben werden soll
     *
     * @return Anzahl der betroffenen Zeilen
     */
    int updateRecipe(String id, Recipe recipe);

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

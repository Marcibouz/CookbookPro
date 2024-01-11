package com.example.first_second.local_memory;

import java.util.List;

/**
 * Schnittstelle des LocalMemory
 */
public interface LocalMemory {

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
     * @return Alle Rezepte der Datenbank
     */
    List<Recipe> readAllRecipes();

    /**
     * Aktualisiert das Rezept mit dem neuen Rezept.
     *
     * @param recipe Rezept mit welchem das alte überschrieben werden soll
     * @return Anzahl der betroffenenen Zeilen
     */
    int updateRecipe(Recipe recipe);

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

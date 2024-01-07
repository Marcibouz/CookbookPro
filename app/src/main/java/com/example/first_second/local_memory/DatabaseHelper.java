package com.example.first_second.local_memory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RecipeList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_recipes";
    private static final String ID = "id";
    private static final String RECIPE_NAME = "recipe_name";
    private static final String INGREDIENTS = "ingredients";
    private static final String DIRECTIONS = "directions";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RECIPE_NAME + " TEXT, " +
                        INGREDIENTS + " TEXT, " +
                        DIRECTIONS + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Fügt ein Rezept der Datenbank hinzu.
     * @param recipe_name Name des Rezepts
     * @param ingredients Zutaten des Rezepts
     * @param directions Zubereitung des Rezepts
     * @return ID des neu hinzugefügten Rezepts oder -1 wenn es einen Fehler beim Hinzufügen gab
     */
    public long addRecipe(String recipe_name, String ingredients, String directions){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_NAME, recipe_name);
        cv.put(INGREDIENTS, ingredients);
        cv.put(DIRECTIONS, directions);

        return db.insert(TABLE_NAME,null,cv);
    }

    //Gibt alle Rezepte der Datenbank wieder
    public Cursor readAllRecipes(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            return db.rawQuery(query, null);
        } else {
            return null;
        }
    }

    public long updateRecipe(String id, String recipe_name, String ingredients, String directions){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_NAME, recipe_name);
        cv.put(INGREDIENTS, ingredients);
        cv.put(DIRECTIONS, directions);

        return db.update(TABLE_NAME, cv, "id=?", new String[]{id});
    }

    public long deleteOneRecipe(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id=?", new String[]{id});
    }

    public void deleteAllRecipes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}

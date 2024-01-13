package com.example.first_second.memory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class MemoryImpl extends SQLiteOpenHelper implements Memory {
    private static final String DATABASE_NAME = "RecipeList.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_recipes";
    private static final String ID = "id";
    private static final String RECIPE_NAME = "recipe_name";
    private static final String INGREDIENTS = "ingredients";
    private static final String DIRECTIONS = "directions";
    private static MemoryImpl memoryImpl;
    private List<MemoryObserver> observers = new LinkedList<>();

    private MemoryImpl(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static MemoryImpl getMemoryImpl(@Nullable Context context) {
        if (memoryImpl == null){
            memoryImpl = new MemoryImpl(context);
        }
        return memoryImpl;
    }

    @Override
    public void addNewDatabaseObserver(MemoryObserver memoryObserver){
        if (!observers.contains(memoryObserver)){
            observers.add(memoryObserver);
        }
    }
    private void notifyObservers(){
        for (MemoryObserver memoryObserver : observers){
            memoryObserver.recipeListChanged();
        }
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

    @Override
    public long addRecipe(Recipe recipe){
        if(recipe == null){
            return -1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_NAME, recipe.getRecipeName());
        cv.put(INGREDIENTS, recipe.getIngredients());
        cv.put(DIRECTIONS, recipe.getDirections());

        long addFeedback = db.insert(TABLE_NAME,null,cv);
        notifyObservers();
        return addFeedback;
    }

    @Override
    public List<Recipe> readAllRecipes(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        LinkedList<Recipe> recipeList = new LinkedList<>();
        if(db != null){
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()){
                Recipe recipe = new Recipe(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3));
                recipe.setId(cursor.getString(0));
                recipeList.add(recipe);
            }
            cursor.close();
        }
        return recipeList;
    }

    @Override
    public int updateRecipe(String id, Recipe recipe){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_NAME, recipe.getRecipeName());
        cv.put(INGREDIENTS, recipe.getIngredients());
        cv.put(DIRECTIONS, recipe.getDirections());

        int affectedRows = db.update(TABLE_NAME, cv, "id=?", new String[]{id});
        notifyObservers();
        return affectedRows;
    }

    @Override
    public int deleteOneRecipe(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int affectedRows = db.delete(TABLE_NAME, "id=?", new String[]{id});
        notifyObservers();
        return affectedRows;
    }

    @Override
    public void deleteAllRecipes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        notifyObservers();
    }
}

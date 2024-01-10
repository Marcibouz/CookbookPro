package com.example.first_second;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.database.Cursor;

import com.example.first_second.local_memory.DatabaseHelper;

public class DatabaseHelperTest {
    DatabaseHelper db;


    @Before
    public void setup(){
        db = new DatabaseHelper(null);
    }
    @Test
    public void OneRecipeAddedCorrectly(){
        db.addRecipe("Recipe", "Toast, Butter", "Apply to Bread");
        Cursor cursor = db.readAllRecipes();
        while (cursor.moveToNext()){
            assertEquals(cursor.getString(0), "1");
            assertEquals(cursor.getString(1), "Recipe");
            assertEquals(cursor.getString(2), "Toast, Butter");
            assertEquals(cursor.getString(3), "Apply to Bread");
        }
    }
}

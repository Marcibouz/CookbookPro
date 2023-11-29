package com.example.first_second.local_memory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "RecipeList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_recipes";

    private static final String USER_NAME = "user_name";
    private static final String RECIPE_NAME = "recipe_name";
    private static final String INGREDIENT = "ingredient";
    private static final String AMOUNT = "amount";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + USER_NAME + " TEXT PRIMARY KEY, " +
                        RECIPE_NAME + " TEXT PRIMARY KEY, " +
                        INGREDIENT + " TEXT PRIMARY KEY, " +
                        AMOUNT + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

package com.example.first_second.gui;

import com.example.first_second.local_memory.DatabaseHelper;

public interface DatabaseObserver {
    void recipeListChanged(DatabaseHelper databaseHelper);
}

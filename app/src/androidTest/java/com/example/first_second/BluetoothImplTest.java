package com.example.first_second;

import com.example.first_second.bluetooth.BluetoothImpl;
import com.example.first_second.gui.MainActivity;
import com.example.first_second.memory.Recipe;

import static org.junit.Assert.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BluetoothImplTest {
    Recipe recipe;
    Recipe recipeNull;
    Recipe recipeEmpty;
    BluetoothImpl bluetooth;

    @Before
    public void setUp() {
        recipe = new Recipe("Suppe", "Wasser", "Einfach trinken");
        recipeEmpty = new Recipe("", "", "");
        recipeNull = null;
        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> { bluetooth = new BluetoothImpl();});
        }
    }

    @Test
    public void testSerializationAndDeserialization() {
        // Serialize the Recipe
        byte[] serializedRecipe = bluetooth.serializeRecipe(recipe);

        // Deserialize the Recipe
        Recipe deserializedRecipe = bluetooth.deserializeRecipe(serializedRecipe);

        // Verify that the deserialized Recipe has the same values as the original
        assertEquals(recipe.getId(), deserializedRecipe.getId());
        assertEquals(recipe.getRecipeName(), deserializedRecipe.getRecipeName());
        assertEquals(recipe.getIngredients(), deserializedRecipe.getIngredients());
        assertEquals(recipe.getDirections(), deserializedRecipe.getDirections());
    }

    @Test
    public void testSerializationWithNullInput() {
        // Serialize with a null Recipe
        byte[] serializedRecipe = bluetooth.serializeRecipe(recipeNull);

        // Serialization should return null
        assertNull(serializedRecipe);
    }

    @Test
    public void testDeserializationWithInvalidInput() {
        // Try to deserialize an invalid byte array
        Recipe deserializedRecipe = bluetooth.deserializeRecipe(new byte[]{});

        // Deserialization should return null for invalid input
        assertNull(deserializedRecipe);
    }

    @Test
    public void testInvalidSerializationAndDeserialization() {
        // Serialize the Recipe
        byte[] serializedRecipeNull = bluetooth.serializeRecipe(recipeNull);

        // Deserialize the Recipe
        Recipe deserializedRecipeNull = bluetooth.deserializeRecipe(serializedRecipeNull);

        // Verify that the deserialized Recipe has the same values as the original
        assertNull(deserializedRecipeNull);
    }
}

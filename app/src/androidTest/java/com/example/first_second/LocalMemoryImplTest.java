package com.example.first_second;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.first_second.local_memory.LocalMemoryImpl;
import com.example.first_second.local_memory.Recipe;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class LocalMemoryImplTest {
    LocalMemoryImpl localMemory;
    Recipe recipe;
    Recipe recipe1;
    Recipe recipe2;
    Recipe recipe3;
    Recipe recipe4;
    Recipe recipe5;
    Recipe recipe6;
    Recipe recipe7;

    @Before
    public void setUp() {
        localMemory = LocalMemoryImpl.getDatabaseHelper(InstrumentationRegistry.getInstrumentation().
                getTargetContext());
        localMemory.deleteAllRecipes();
        recipe = new Recipe("Name", "Kartoffel", "Kochen");
        recipe1 = new Recipe("Name1", "Kartoffel1", "Kochen1");
        recipe2 = new Recipe("Name2", "Kartoffel2", "Kochen2");
        recipe3 = new Recipe("Name3", "Kartoffel3", "Kochen3");
        recipe4 = new Recipe("Name4", "Kartoffel4", "Kochen4");
        recipe5 = new Recipe("Name5", "Kartoffel5", "Kochen5");
        recipe6 = new Recipe("Name6", "Kartoffel6", "Kochen6");
        recipe7 = new Recipe("Name7", "Kartoffel7", "Kochen7");
    }

    @After
    public void finish(){
        localMemory.close();
    }
    @Test
    public void testOneRecipeAdded() {
        localMemory.addRecipe(recipe);
        List<Recipe> recipes = localMemory.readAllRecipes();
        assertEquals(1, recipes.size());
        assertEquals(recipe.getRecipeName(), recipes.get(0).getRecipeName());
        assertEquals(recipe.getIngredients(), recipes.get(0).getIngredients());
        assertEquals(recipe.getDirections(), recipes.get(0).getDirections());
    }

    @Test
    public void testManyRecipesAdded() {
        localMemory.addRecipe(recipe);
        localMemory.addRecipe(recipe1);
        localMemory.addRecipe(recipe2);
        localMemory.addRecipe(recipe3);
        localMemory.addRecipe(recipe4);
        localMemory.addRecipe(recipe5);
        localMemory.addRecipe(recipe6);
        localMemory.addRecipe(recipe7);
        List<Recipe> recipes = localMemory.readAllRecipes();
        assertEquals(8, recipes.size());
    }

    @Test
    public void testRecipeAddedNull() {
        List<Recipe> recipes = localMemory.readAllRecipes();
        assertEquals(0, recipes.size());
        long id = localMemory.addRecipe(null);
        assertEquals(0, recipes.size());
        assertEquals(-1, id);
    }

    @Test
    public void testDeleteAllRecipes() {
        localMemory.addRecipe(recipe);
        localMemory.addRecipe(recipe1);
        localMemory.addRecipe(recipe2);
        localMemory.deleteAllRecipes();
        List<Recipe> recipes = localMemory.readAllRecipes();
        assertEquals(0, recipes.size());
    }

    @Test
    public void testDeleteOneRecipe() {
        long id = localMemory.addRecipe(recipe);
        localMemory.addRecipe(recipe1);

        List<Recipe> recipes = localMemory.readAllRecipes();
        assertEquals(2, recipes.size());

        int numberOfAffectedRows = localMemory.deleteOneRecipe(String.valueOf(id));
        recipes = localMemory.readAllRecipes();

        assertEquals(1, recipes.size());
        assertEquals(recipe1.getRecipeName(), recipes.get(0).getRecipeName());
        assertEquals(recipe1.getIngredients(), recipes.get(0).getIngredients());
        assertEquals(recipe1.getDirections(), recipes.get(0).getDirections());
        assertEquals(1, numberOfAffectedRows);
    }

    @Test
    public void testDeleteRecipeNotThere() {
        List<Recipe> recipes = localMemory.readAllRecipes();
        assertEquals(0, recipes.size());
        int numberOfAffectedRows = localMemory.deleteOneRecipe("1");
        assertEquals(0, numberOfAffectedRows);
    }

    @Test
    public void testUpdateRecipe() {
        long id = localMemory.addRecipe(recipe);

        List<Recipe> recipes = localMemory.readAllRecipes();
        assertEquals(1, recipes.size());
        assertEquals(recipe.getRecipeName(), recipes.get(0).getRecipeName());
        assertEquals(recipe.getIngredients(), recipes.get(0).getIngredients());
        assertEquals(recipe.getDirections(), recipes.get(0).getDirections());

        Recipe recipe1 = new Recipe("Name1", "Kartoffel1", "Kochen1");
        int numberOfAffectedRows = localMemory.updateRecipe(String.valueOf(id), recipe1);

        recipes = localMemory.readAllRecipes();
        assertEquals(1, recipes.size());
        assertEquals(recipe1.getRecipeName(), recipes.get(0).getRecipeName());
        assertEquals(recipe1.getIngredients(), recipes.get(0).getIngredients());
        assertEquals(recipe1.getDirections(), recipes.get(0).getDirections());
        assertEquals(1, numberOfAffectedRows);
    }

    @Test
    public void testUpdateRecipeNotThere() {
        List<Recipe> recipes = localMemory.readAllRecipes();
        assertEquals(0, recipes.size());
        int numberOfAffectedRows = localMemory.updateRecipe("1", recipe);
        assertEquals(0, numberOfAffectedRows);
    }

    @Test
    public void testComplexAddUpdateDelete() {
        long id = localMemory.addRecipe(recipe);
        long id1 = localMemory.addRecipe(recipe1);
        localMemory.addRecipe(recipe2);

        List<Recipe> recipes = localMemory.readAllRecipes();
        assertEquals(3, recipes.size());

        localMemory.deleteOneRecipe(String.valueOf(id));
        localMemory.deleteOneRecipe(String.valueOf(id1));

        recipes = localMemory.readAllRecipes();
        assertEquals(1, recipes.size());
        assertEquals(recipe2.getRecipeName(), recipes.get(0).getRecipeName());
        assertEquals(recipe2.getIngredients(), recipes.get(0).getIngredients());
        assertEquals(recipe2.getDirections(), recipes.get(0).getDirections());

        localMemory.addRecipe(recipe3);
        localMemory.addRecipe(recipe4);
        localMemory.addRecipe(recipe5);

        recipes = localMemory.readAllRecipes();
        assertEquals(4, recipes.size());
        assertEquals(recipe4.getRecipeName(), recipes.get(2).getRecipeName());
        assertEquals(recipe4.getIngredients(), recipes.get(2).getIngredients());
        assertEquals(recipe4.getDirections(), recipes.get(2).getDirections());

        localMemory.deleteAllRecipes();

        recipes = localMemory.readAllRecipes();
        assertEquals(0, recipes.size());
    }
}
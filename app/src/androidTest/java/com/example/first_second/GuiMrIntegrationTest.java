package com.example.first_second;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import static org.junit.Assert.assertEquals;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.first_second.gui.MainActivity;
import com.example.first_second.memory.MemoryImpl;
import com.example.first_second.memory.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class GuiMrIntegrationTest {
    @Rule
    public GrantPermissionRule bluetoothPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.BLUETOOTH);
    @Rule
    public GrantPermissionRule bluetoothPermissionRule1 = GrantPermissionRule.grant(android.Manifest.permission.BLUETOOTH_ADMIN);
    @Rule
    public GrantPermissionRule bluetoothPermissionRule2 = GrantPermissionRule.grant(android.Manifest.permission.BLUETOOTH_ADVERTISE);
    @Rule
    public GrantPermissionRule bluetoothPermissionRule3 = GrantPermissionRule.grant(android.Manifest.permission.BLUETOOTH_CONNECT);
    @Rule
    public GrantPermissionRule bluetoothPermissionRule4 = GrantPermissionRule.grant(android.Manifest.permission.BLUETOOTH_SCAN);
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    MemoryImpl memoryImpl;
    @Before
    public void setUp() {
        memoryImpl = MemoryImpl.getMemoryImpl(getInstrumentation().
                getTargetContext());
        memoryImpl.deleteAllRecipes();
    }

    @After
    public void finish() {
        memoryImpl.close();
    }

    @Test
    public void testGuiOneRecipeAdded() {
        String testName = "Spaghetti Bolognese";
        String testIngredients =
                "500g Spaghetti, 500g Hack, 1000L Tomatensauce, 1 Haufen Parmesan.";
        String testDirections =
                "Einfach Kochen bitte";
        String resultName = "Spaghetti Bolognese";
        String resultIngredients =
                "500g Spaghetti, 500g Hack, 1000L Tomatensauce, 1 Haufen Parmesan.";
        String resultDirections =
                "Einfach Kochen bitte";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testIngredients),
                closeSoftKeyboard());
        onView(withId(R.id.directions)).perform(typeText(testDirections),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText(testName)).perform(click());

        onView(withId(R.id.recipe_name2)).check(matches(withText(resultName)));
        onView(withId(R.id.ingredients2)).check(matches(withText(resultIngredients)));
        onView(withId(R.id.directions2)).check(matches(withText(resultDirections)));
    }

    // sind alle Rezepte inhaltlich korrekt und alle am ende in der rezeptliste vorhanden ->
    // Rezepte ansehen
    @Test
    public void testGuiManyRecipesAdded() {
        String testName = "Auflauf";
        String testIngredients =
                "500g Auflauf, 500g Hack, 1000L Auflaufsauce, 1 Haufen Parmesan.";
        String testDirections =
                "Schnell Kochen!";
        String resultName = "Auflauf";
        String resultIngredients =
                "500g Auflauf, 500g Hack, 1000L Auflaufsauce, 1 Haufen Parmesan.";
        String resultDirections =
                "Schnell Kochen!";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testIngredients),
                closeSoftKeyboard());
        onView(withId(R.id.directions)).perform(typeText(testDirections),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText(testName)).perform(click());
        onView(withId(R.id.recipe_name2)).check(matches(withText(resultName)));
        onView(withId(R.id.ingredients2)).check(matches(withText(resultIngredients)));
        onView(withId(R.id.directions2)).check(matches(withText(resultDirections)));

        onView(withContentDescription("Navigate up")).perform(click());

        String testName1 = "Rollade";
        String testIngredients1 =
                "1000g Rollade, 1000g Rollade, 5L Rollade, 2,43234 Zwiebeln.";
        String testDirections1 =
                "Schnell Kochen Bitte!";
        String resultName1 = "Rollade";
        String resultIngredients1 =
                "1000g Rollade, 1000g Rollade, 5L Rollade, 2,43234 Zwiebeln.";
        String resultDirections1 =
                "Schnell Kochen Bitte!";

        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName1),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testIngredients1),
                closeSoftKeyboard());
        onView(withId(R.id.directions)).perform(typeText(testDirections1),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText(testName1)).perform(click());
        onView(withId(R.id.recipe_name2)).check(matches(withText(resultName1)));
        onView(withId(R.id.ingredients2)).check(matches(withText(resultIngredients1)));
        onView(withId(R.id.directions2)).check(matches(withText(resultDirections1)));

        onView(withContentDescription("Navigate up")).perform(click());

        String testName2 = "Butter Chicken";
        String testIngredients2 =
                "300g Reis, 1x Dose passierte Tomaten, 1x Sahne, viel Curry und Chicken.";
        String testDirections2 =
                "ICH HAB HUNGER!";
        String resultName2 = "Butter Chicken";
        String resultIngredients2 =
                "300g Reis, 1x Dose passierte Tomaten, 1x Sahne, viel Curry und Chicken.";
        String resultDirections2 =
                "ICH HAB HUNGER!";

        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName2),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testIngredients2),
                closeSoftKeyboard());
        onView(withId(R.id.directions)).perform(typeText(testDirections2),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText(testName2)).perform(click());
        onView(withId(R.id.recipe_name2)).check(matches(withText(resultName2)));
        onView(withId(R.id.ingredients2)).check(matches(withText(resultIngredients2)));
        onView(withId(R.id.directions2)).check(matches(withText(resultDirections2)));

        onView(withContentDescription("Navigate up")).perform(click());

        onView(withText(testName)).check(matches(isDisplayed()));
        onView(withText(testName1)).check(matches(isDisplayed()));
        onView(withText(testName2)).check(matches(isDisplayed()));
    }

    // delete all button löscht alle rezepte und diese werden danach nicht mehr angezeigt -> alle löschen
    // (background pick?)
    @Test
    public void testGuiDeleteAllRecipes() {
        String testName = "Auflauf";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        String testName1 = "Spaghetti Bolognese";
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName1),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        String testName2 = "Butter Chicken";
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName2),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        String testName3 = "Schnitzel";
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName3),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Delete All")).perform(click());

        onView(withText(testName)).check(doesNotExist());
        onView(withText(testName1)).check(doesNotExist());
        onView(withText(testName2)).check(doesNotExist());
        onView(withText(testName3)).check(doesNotExist());
    }

    //Ein rezept löschen use case
    @Test
    public void testGuiDeleteOneRecipe() {
        String testName = "Auflauf";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        String testName1 = "Spaghetti Bolognese";
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName1),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        onView(withText(testName1)).perform(click());
        onView(withId(R.id.delete_button)).perform(click());
        onView(withText(testName)).check(matches(isDisplayed()));
        onView(withText(testName1)).check(doesNotExist());
    }

    // doppelt löschen mit zurückpfeil
    @Test
    public void testGuiDeleteRecipeNotThere() {
        String testName = "Auflauf";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        onView(withText(testName)).perform(click());
        onView(withId(R.id.delete_button)).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.delete_button)).perform(click());
        onView(withId(R.id.recipeScreen)).check(matches(isDisplayed()));
    }

    @Test
    public void testGuiUpdateRecipe() {
        String testName = "Rollade";
        String testIngredients =
                "1000g Rollade, 1000g Rollade, 5L Rollade, 2,43234 Zwiebeln.";
        String testDirections =
                "Schnell Kochen Bitte!";
        String updateName = "Butter Chicken";
        String updateIngredients =
                "300g Reis, 1x Dose passierte Tomaten, 1x Sahne, viel Curry und Chicken.";
        String updateDirections =
                "ICH HAB HUNGER!";

        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testIngredients),
                closeSoftKeyboard());
        onView(withId(R.id.directions)).perform(typeText(testDirections),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        onView(withText(testName)).perform(click());
        onView(withId(R.id.recipe_name2)).perform(clearText());
        onView(withId(R.id.recipe_name2)).perform(typeText(updateName),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients2)).perform(clearText());
        onView(withId(R.id.ingredients2)).perform(typeText(updateIngredients),
                closeSoftKeyboard());
        onView(withId(R.id.directions2)).perform(clearText());
        onView(withId(R.id.directions2)).perform(typeText(updateDirections),
                closeSoftKeyboard());
        onView(withId(R.id.update_button)).perform(click());

        onView(withText(updateName)).check(matches(isDisplayed()));
        onView(withText(updateName)).perform(click());
        onView(withId(R.id.recipe_name2)).check(matches(withText(updateName)));
        onView(withId(R.id.ingredients2)).check(matches(withText(updateIngredients)));
        onView(withId(R.id.directions2)).check(matches(withText(updateDirections)));
    }

    @Test
    public void testGuiUpdateRecipeNotThere() {
        String testName = "Auflauf";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());

        onView(withText(testName)).perform(click());
        onView(withId(R.id.delete_button)).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.update_button)).perform(click());
        onView(withId(R.id.recipeScreen)).check(matches(isDisplayed()));
    }

    @Test
    public void testGuiComplexAddUpdateDelete() {
        //1 Rezept adden
        String testName = "Spaghetti Bolognese";
        String testIngredients =
                "500g Spaghetti, 500g Hack, 1000L Tomatensauce, 1 Haufen Parmesan.";
        String testDirections =
                "Einfach Kochen bitte";
        String resultName = "Spaghetti Bolognese";
        String resultIngredients =
                "500g Spaghetti, 500g Hack, 1000L Tomatensauce, 1 Haufen Parmesan.";
        String resultDirections =
                "Einfach Kochen bitte";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testIngredients),
                closeSoftKeyboard());
        onView(withId(R.id.directions)).perform(typeText(testDirections),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText(testName)).perform(click());

        onView(withId(R.id.recipe_name2)).check(matches(withText(resultName)));
        onView(withId(R.id.ingredients2)).check(matches(withText(resultIngredients)));
        onView(withId(R.id.directions2)).check(matches(withText(resultDirections)));

        //Rezept aktualisieren
        String updateName = "Butter Chicken";
        String updateIngredients =
                "300g Reis, 1x Dose passierte Tomaten, 1x Sahne, viel Curry und Chicken.";
        String updateDirections =
                "ICH HAB HUNGER!";

        onView(withId(R.id.recipe_name2)).perform(clearText());
        onView(withId(R.id.recipe_name2)).perform(typeText(updateName),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients2)).perform(clearText());
        onView(withId(R.id.ingredients2)).perform(typeText(updateIngredients),
                closeSoftKeyboard());
        onView(withId(R.id.directions2)).perform(clearText());
        onView(withId(R.id.directions2)).perform(typeText(updateDirections),
                closeSoftKeyboard());
        onView(withId(R.id.update_button)).perform(click());

        onView(withText(updateName)).check(matches(isDisplayed()));
        onView(withText(updateName)).perform(click());
        onView(withId(R.id.recipe_name2)).check(matches(withText(updateName)));
        onView(withId(R.id.ingredients2)).check(matches(withText(updateIngredients)));
        onView(withId(R.id.directions2)).check(matches(withText(updateDirections)));

        onView(withContentDescription("Navigate up")).perform(click());

        //2 weitere Rezepte adden

        String testName1 = "Rollade";
        String testIngredients1 =
                "1000g Rollade, 1000g Rollade, 5L Rollade, 2,43234 Zwiebeln.";
        String testDirections1 =
                "Schnell Kochen Bitte!";
        String resultName1 = "Rollade";
        String resultIngredients1 =
                "1000g Rollade, 1000g Rollade, 5L Rollade, 2,43234 Zwiebeln.";
        String resultDirections1 =
                "Schnell Kochen Bitte!";

        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName1),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testIngredients1),
                closeSoftKeyboard());
        onView(withId(R.id.directions)).perform(typeText(testDirections1),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText(testName1)).perform(click());
        onView(withId(R.id.recipe_name2)).check(matches(withText(resultName1)));
        onView(withId(R.id.ingredients2)).check(matches(withText(resultIngredients1)));
        onView(withId(R.id.directions2)).check(matches(withText(resultDirections1)));

        onView(withContentDescription("Navigate up")).perform(click());

        String testName2 = "Auflauf";
        String testIngredients2 =
                "500g Auflauf, 500g Hack, 1000L Auflaufsauce, 1 Haufen Parmesan.";
        String testDirections2 =
                "Schnell Kochen!";
        String resultName2 = "Auflauf";
        String resultIngredients2 =
                "500g Auflauf, 500g Hack, 1000L Auflaufsauce, 1 Haufen Parmesan.";
        String resultDirections2 =
                "Schnell Kochen!";
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText(testName2),
                closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testIngredients2),
                closeSoftKeyboard());
        onView(withId(R.id.directions)).perform(typeText(testDirections2),
                closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText(testName2)).perform(click());
        onView(withId(R.id.recipe_name2)).check(matches(withText(resultName2)));
        onView(withId(R.id.ingredients2)).check(matches(withText(resultIngredients2)));
        onView(withId(R.id.directions2)).check(matches(withText(resultDirections2)));

        onView(withContentDescription("Navigate up")).perform(click());

        //1 Rezept löschen

        onView(withText(testName2)).perform(click());
        onView(withId(R.id.delete_button)).perform(click());
        onView(withText(updateName)).check(matches(isDisplayed()));
        onView(withText(testName1)).check(matches(isDisplayed()));
        onView(withText(testName2)).check(doesNotExist());
        //alle Rezepte löschen

        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Delete All")).perform(click());

        onView(withText(updateName)).check(doesNotExist());
        onView(withText(testName1)).check(doesNotExist());
    }
}

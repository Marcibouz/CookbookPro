package com.example.first_second;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.os.Build;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.example.first_second.gui.MainActivity;
import com.example.first_second.memory.MemoryImpl;
import com.example.first_second.memory.Recipe;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BluetoothEndToEndTest {

    @Rule
    public GrantPermissionRule bluetoothPermissionRule = GrantPermissionRule.grant
            (android.Manifest.permission.BLUETOOTH);
    @Rule
    public GrantPermissionRule bluetoothPermissionRule1 = GrantPermissionRule.grant
            (android.Manifest.permission.BLUETOOTH_ADMIN);
    @Rule
    public GrantPermissionRule bluetoothPermissionRule2 = GrantPermissionRule.grant
            (android.Manifest.permission.BLUETOOTH_ADVERTISE);
    @Rule
    public GrantPermissionRule bluetoothPermissionRule3 = GrantPermissionRule.grant
            (android.Manifest.permission.BLUETOOTH_CONNECT);
    @Rule
    public GrantPermissionRule bluetoothPermissionRule4 = GrantPermissionRule.grant
            (android.Manifest.permission.BLUETOOTH_SCAN);
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    MemoryImpl memoryImpl;
    // Replace with own device name when trying to send Recipe to another device
    String deviceName = "Galaxy Tab S2";
    // Replace with own Recipe name when trying to receive Recipe from another device
    String receivedRecipeName = "Kartoffelsalat";


    @Before
    public void setUp() {
        memoryImpl = MemoryImpl.getMemoryImpl(getInstrumentation().getTargetContext());
        memoryImpl.deleteAllRecipes();
    }

    @Test
    public void testUseCaseSendRecipe() {
        String testName = "Spaghetti";
        String testIngredients =
                "500g Spaghetti";
        String testDirections =
                "Einfach Kochen";
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

        onView(withId(R.id.recipe_name2)).check(matches(withText(testName)));
        onView(withId(R.id.ingredients2)).check(matches(withText(testIngredients)));
        onView(withId(R.id.directions2)).check(matches(withText(testDirections)));

        onView(withId(R.id.share_button)).perform(click());
        onView(withText(deviceName)).perform(click());
        onView(withContentDescription(androidx.appcompat.R.string.abc_action_bar_up_description))
                .perform(click());
        onView(withId(R.id.multiplier)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.ingredients2)).check(matches(withText("1000g Spaghetti")));

        onView(withId(R.id.delete_button)).perform(click());

        onView(withText(testName)).check(doesNotExist());
    }

    @Test
    public void testUseCaseReceiveRecipe() {
        onView(withId(R.id.splashScreen)).perform(click());
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Receive via Bluetooth")).perform(click());

        onView(withText(receivedRecipeName)).check(matches(isDisplayed()));

        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Delete All")).perform(click());

        onView(withText(receivedRecipeName)).check(doesNotExist());
    }
}

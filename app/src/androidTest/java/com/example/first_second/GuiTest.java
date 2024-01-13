package com.example.first_second;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.example.first_second.gui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class GuiTest {
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
    @Test
    public void testSplashScreenToRecipeListScreen() {
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.recipeListScreen)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddButton() {
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.addScreen)).check(matches(isDisplayed()));
    }

    @Test
    public void testSaveButton() {
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.save_button)).perform(click());
        onView(withId(R.id.recipeListScreen)).check(matches(isDisplayed()));
    }

    @Test
    public void testRowElementClick() {
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("A"), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("A")).perform(click());
        onView(withId(R.id.recipeScreen)).check(matches(isDisplayed()));
    }

    @Test
    public void testUpdateButton() {
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("B"), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("B")).perform(click());
        onView(withId(R.id.update_button)).perform(click());
        onView(withId(R.id.recipeListScreen)).check(matches(isDisplayed()));
    }

    @Test
    public void testShareButton() {
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("C"), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("C")).perform(click());
        onView(withId(R.id.share_button)).perform(click());
        onView(withId(R.id.availableDevicesScreen)).check(matches(isDisplayed()));
    }
    @Test
    public void testDeleteButton() {
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("D"), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("D")).perform(click());
        onView(withId(R.id.delete_button)).perform(click());
        onView(withId(R.id.recipeListScreen)).check(matches(isDisplayed()));
    }
}

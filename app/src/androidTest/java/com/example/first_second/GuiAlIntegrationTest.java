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
public class GuiAlIntegrationTest {
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
    public void testGuiCommonRecipe() {
        String testString = "500g Hack, 1 Zwiebel, 800g Dose Tomaten, 2,5 Briesen Salz";
        String resultString = "1000g Hack, 2 Zwiebel, 1600g Dose Tomaten, 5 Briesen Salz";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("A"), closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("A")).perform(click());
        onView(withId(R.id.multiplier)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.ingredients2)).check(matches(withText(resultString)));
    }
    @Test
    public void testGuiMultiplierIsZero() {
        String testString = "3 Eier und 5,5 Karoffeln.";
        String resultString = "3 Eier und 5,5 Karoffeln.";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("B"), closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("B")).perform(click());
        onView(withId(R.id.multiplier)).perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.ingredients2)).check(matches(withText(resultString)));
    }
    @Test
    public void testGuiMultiplierSmallerThanZero() {
        String testString = "3 Eier und 5,5 Karoffeln.";
        String resultString = "3 Eier und 5,5 Karoffeln.";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("C"), closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("C")).perform(click());
        onView(withId(R.id.multiplier)).perform(typeText("-1"), closeSoftKeyboard());
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.ingredients2)).check(matches(withText(resultString)));
    }

    @Test
    public void testGuiNoNumbers() {
        String testString = "Ich bereite ein Rezept zu";
        String resultString = "Ich bereite ein Rezept zu";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("D"), closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("D")).perform(click());
        onView(withId(R.id.multiplier)).perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.ingredients2)).check(matches(withText(resultString)));
    }

    @Test
    public void testGuiCommaStaysComma() {
        String testString = "123,123 14124,2324 234,234,234 3454,3454";
        String resultString = "369,369 42372,6972 702,702,702 10363,0362";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("E"), closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("E")).perform(click());
        onView(withId(R.id.multiplier)).perform(typeText("3"), closeSoftKeyboard());
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.ingredients2)).check(matches(withText(resultString)));
    }

    @Test
    public void testGuiDotStaysDot() {
        String testString = "123.123 14124.2324 234.234.234 3454.3454";
        String resultString = "369.369 42372.6972 702.702.702 10363.0362";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("F"), closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("F")).perform(click());
        onView(withId(R.id.multiplier)).perform(typeText("3"), closeSoftKeyboard());
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.ingredients2)).check(matches(withText(resultString)));
    }

    @Test
    public void testGuiComplexExample() {
        String testString = "123,123adw14124.2324.234adw234,234,234dw3454.3454";
        String resultString = "369,369adw42372.6972.702adw702,702,702dw10363.0362";
        onView(withId(R.id.splashScreen)).perform(click());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.recipe_name)).perform(typeText("G"), closeSoftKeyboard());
        onView(withId(R.id.ingredients)).perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("G")).perform(click());
        onView(withId(R.id.multiplier)).perform(typeText("3"), closeSoftKeyboard());
        onView(withId(R.id.calculate_button)).perform(click());
        onView(withId(R.id.ingredients2)).check(matches(withText(resultString)));
    }
}

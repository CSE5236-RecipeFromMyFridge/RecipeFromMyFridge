package com.example.recipefrommyfridgeapp.ui.login;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.recipefrommyfridgeapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest() {
        ViewInteraction textView = onView(
                allOf(withText("Recipe From My Fridge App"),
                        withParent(allOf(withId(androidx.appcompat.R.id.action_bar),
                                withParent(withId(androidx.appcompat.R.id.action_bar_container)))),
                        isDisplayed()));
        textView.check(matches(withText("Recipe From My Fridge App")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.appName), withText("Recipe From Fridge"),
                        withParent(withParent(withId(R.id.fragment_container))),
                        isDisplayed()));
        textView2.check(matches(withText("Recipe From Fridge")));

        ViewInteraction button = onView(
                allOf(withId(R.id.fragment_login_guestAccount), withText("CONTINUE AS GUEST"),
                        withParent(withParent(withId(R.id.fragment_container))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.fragment_login_createAccount), withText("CREATE ACCOUNT"),
                        withParent(withParent(withId(R.id.fragment_container))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.fragment_login_login), withText("LOGIN"),
                        withParent(withParent(withId(R.id.fragment_container))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));
    }
}

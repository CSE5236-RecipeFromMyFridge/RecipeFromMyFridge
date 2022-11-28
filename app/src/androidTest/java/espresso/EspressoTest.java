package espresso;

import android.app.Activity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.recipefrommyfridgeapp.ui.*;
import com.example.recipefrommyfridgeapp.ui.login.LoginActivity;
import com.example.recipefrommyfridgeapp.R;
import com.example.recipefrommyfridgeapp.ui.login.LoginFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

//    @Before
//    public void init(){
//        launchFragmentInContainer
//    }

    @Test
    public void passwordIsDisplayedAboveLoginButton()
    {
//        launchFragment<LoginFragment>();
        onView(withId(R.id.appName)).check(matches(isDisplayed()));
        onView(withId(R.id.appName)).check(isCompletelyAbove(withId(R.id.fragment_login_email)));
    }
}

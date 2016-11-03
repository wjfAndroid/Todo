package com.wjft.odo;

import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.wjft.odo.view.Tset.TestActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(JUnit4.class)
@LargeTest
public class TestActivityInstrumentationTest {
    @Rule
    public ActivityTestRule<TestActivity> mRule = new ActivityTestRule<TestActivity>(TestActivity.class);

    @Test
    public void sayHello() {
        onView(withId(R.id.et_tset)).perform(typeText("json"), closeSoftKeyboard());
        onView(withId(R.id.bt_tset)).perform(click());

        onView(withId(R.id.tv_tset)).check(matches(withText("hellojson")));
//        onView(withText("hellojson"))
//                .check(matches(isDisplayed()));
    }

}

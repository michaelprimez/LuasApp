package com.michaelkeskinidis.luasapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.michaelkeskinidis.luasapp.ui.activities.MainActivity
import com.michaelkeskinidis.luasapp.utils.withCustomConstraints
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LuasForecastFragmentTest {

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun fragmentViewsVisibilityChecks() {
        onView(withId(R.id.tramList)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.swipe_container)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test fun fragmentSwipeRefreshLayout() {
        onView(withId(R.id.swipe_container)).perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
    }
}
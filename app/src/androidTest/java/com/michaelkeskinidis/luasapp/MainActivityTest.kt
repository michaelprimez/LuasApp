package com.michaelkeskinidis.luasapp

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.michaelkeskinidis.luasapp.ui.activities.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun activityViewsVisibilityChecks() {
        var activityScenarioRule = launchActivity<MainActivity>()
        onView(withId(R.id.message)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.info)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.stopName)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.fab)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun activityClickOnRefreshButton() {
        var activityScenarioRule = launchActivity<MainActivity>()
        onView(withId(R.id.fab)).perform(click())
        // check ...
    }
}
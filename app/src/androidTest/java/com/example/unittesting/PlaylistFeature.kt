package com.example.unittesting

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import com.example.unittesting.remote.CoroutinesModule
import com.example.unittesting.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PlaylistFeature {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlists")
    }

    @Test
    fun displayListOfPlaylists() {
        Thread.sleep(4000)

        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.rv_playlist, 10)

        onView(
            allOf(
                withId(R.id.tv_name),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        )
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_category),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        )
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_playlist),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 1))
            )
        )
            .check(matches(withDrawable(R.drawable.ic_playlist)))
            .check(matches(isDisplayed()))

    }

    @Test
    fun displayLoaderWhileFetchingThePlaylists() {
        assertDisplayed(R.id.loader)
    }

    @Test
    fun displayRockImageForRockListItems() {
        Thread.sleep(4000)

        onView(
            allOf(
                withId(R.id.iv_playlist),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        )
            .check(matches(withDrawable(R.drawable.ic_rock)))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_playlist),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 3))
            )
        )
            .check(matches(withDrawable(R.drawable.ic_rock)))
            .check(matches(isDisplayed()))
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }

    @Test
    fun hideLoaderAfterFetchingThePlaylists() {
        Thread.sleep(4000)
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDetailsScreen() {
        Thread.sleep(4000)

        onView(
            allOf(
                withId(R.id.iv_playlist),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        ).perform(click())

        assertDisplayed(R.id.playlist_details_root)
    }
}
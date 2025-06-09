package com.example.unittesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class PlaylistFeature : BaseTest() {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {

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
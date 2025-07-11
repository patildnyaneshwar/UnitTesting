package com.example.unittesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PlaylistDetailFeature : BaseTest() {

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
        Thread.sleep(4000)

        onView(
            allOf(
                withId(R.id.iv_playlist),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        ).perform(click())

        assertDisplayed("Playlist Detail")
    }

    @Test
    fun displayPlaylistNameAndDetails() {
        Thread.sleep(4000)

        onView(
            allOf(
                withId(R.id.iv_playlist),
                isDescendantOfA(nthChildOf(withId(R.id.rv_playlist), 0))
            )
        ).perform(click())

        assertDisplayed("Hard Rock Cafe")

        assertDisplayed("Rock your senses with this timeless signature vibe list. \\n\\n • Poison \\n • You shook me all night \\n • Zombie \\n • Rock'n Me \\n • Thunderstruck \\n • I Hate Myself for Loving you \\n • Crazy \\n • Knockin' on Heavens Door")
    }

}
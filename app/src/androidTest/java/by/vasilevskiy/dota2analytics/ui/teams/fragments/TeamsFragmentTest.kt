package by.vasilevskiy.dota2analytics.ui.teams.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import by.vasilevskiy.dota2analytics.MainActivity
import by.vasilevskiy.dota2analytics.R
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TeamsFragmentTest {

    @Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchSuccess() {
        onView(withId(R.id.search_view_teams))
    }

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }
}
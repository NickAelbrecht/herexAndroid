package com.example.nick.herexamen


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest5 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest5() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_recepten), withContentDescription("Recepten"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val appCompatTextView = onView(
            allOf(
                withId(R.id.naam), withText("TestApi2"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cart_recycler),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.recipe_detail_title), withText("TestApi2"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("TestApi2")))

        val button = onView(
            allOf(
                withId(R.id.recipe_detail_delete),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.textView8), withText("Producten :"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Producten :")))

        val textView3 = onView(
            allOf(
                withId(R.id.textView9), withText("Allergieën :"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Allergieën :")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}

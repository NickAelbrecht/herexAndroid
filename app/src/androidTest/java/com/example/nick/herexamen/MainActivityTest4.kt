package com.example.nick.herexamen


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
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
class MainActivityTest4 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest4() {
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

        val appCompatButton = onView(
            allOf(
                withId(R.id.cart_button_add), withText("Voeg iets toe aan de lijst"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.newrecipe_producten),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.newrecipe_add_product),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val textView = onView(
            allOf(
                withText("test"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.newrecipe_producten_linear),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("test")))

        val button = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.newrecipe_producten_linear),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.newrecipe_producten_linear),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val button3 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.newrecipe_producten_linear),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        button3.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.newrecipe_producten),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.check(matches(isDisplayed()))
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

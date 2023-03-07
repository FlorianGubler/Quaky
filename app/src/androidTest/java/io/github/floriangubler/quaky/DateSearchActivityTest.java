package io.github.floriangubler.quaky;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DateSearchActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void dateSearchActivityTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.historyButton), withText("History"),
                        childAtPosition(
                                allOf(withId(R.id.mainSiteLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.searchStartInput), withText("1"),
                        withParent(allOf(withId(R.id.filterLimitContainer),
                                withParent(withId(R.id.filterContainer)))),
                        isDisplayed()));
        editText.check(matches(withText("1")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.searchLimitInput), withText("1"),
                        withParent(allOf(withId(R.id.filterLimitContainer),
                                withParent(withId(R.id.filterContainer)))),
                        isDisplayed()));
        editText2.check(matches(withText("1")));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.searchStartDateInput),
                        childAtPosition(
                                allOf(withId(R.id.filterDateContainer),
                                        childAtPosition(
                                                withId(R.id.filterContainer),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("22.03.2022"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.searchEndDateInput),
                        childAtPosition(
                                allOf(withId(R.id.searchButtonAndDateContainer),
                                        childAtPosition(
                                                withId(R.id.filterDateContainer),
                                                1)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("22.04.2022"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                allOf(withId(R.id.searchButtonAndDateContainer),
                                        childAtPosition(
                                                withId(R.id.filterDateContainer),
                                                1)),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("Petersville, Alaska"),
                        withParent(withParent(withId(R.id.resultContainer))),
                        isDisplayed()));
        textView.check(matches(withText("Petersville, Alaska")));

        ViewInteraction textView2 = onView(
                allOf(withText("Apr 21, 2022"),
                        withParent(withParent(withId(R.id.resultContainer))),
                        isDisplayed()));
        textView2.check(matches(withText("Apr 21, 2022")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

package io.github.floriangubler.quaky;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.lastEarthquakeDate), withText("Mar 7, 2023"),
                        withParent(allOf(withId(R.id.lastEarthquakeContainer),
                                withParent(withId(R.id.mainSiteLayout)))),
                        isDisplayed()));
        textView.check(matches(withText("Mar 7, 2023")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.lastEarthquakeCountry), withText("North America"),
                        withParent(allOf(withId(R.id.lastEarthquakeContainer),
                                withParent(withId(R.id.mainSiteLayout)))),
                        isDisplayed()));
        textView2.check(matches(withText("North America")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.lastEarthquakeCountry), withText("North America"),
                        withParent(allOf(withId(R.id.lastEarthquakeContainer),
                                withParent(withId(R.id.mainSiteLayout)))),
                        isDisplayed()));
        textView3.check(matches(withText("North America")));

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.lastEarthquakeContainer),
                        childAtPosition(
                                allOf(withId(R.id.mainSiteLayout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.detailTitle), withText("M 1.2 - 86 km NW of Karluk, Alaska"),
                        withParent(allOf(withId(R.id.mainSiteLayout),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView4.check(matches(withText("M 1.2 - 86 km NW of Karluk, Alaska")));

        ViewInteraction textView5 = onView(
                allOf(withText("Alert: "),
                        withParent(allOf(withId(R.id.detailContainer),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class)))),
                        isDisplayed()));
        textView5.check(matches(withText("Alert: ")));

        pressBack();

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

        ViewInteraction textView6 = onView(
                allOf(withText("Mar 7, 2023"),
                        withParent(withParent(withId(R.id.resultContainer))),
                        isDisplayed()));
        textView6.check(matches(withText("Mar 7, 2023")));

        ViewInteraction textView7 = onView(
                allOf(withText("Karluk, Alaska"),
                        withParent(withParent(withId(R.id.resultContainer))),
                        isDisplayed()));
        textView7.check(matches(withText("Karluk, Alaska")));
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

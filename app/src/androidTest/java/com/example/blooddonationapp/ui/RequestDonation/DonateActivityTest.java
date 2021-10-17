package com.example.blooddonationapp.ui.RequestDonation;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.blooddonationapp.activities.RequestActivity;
import com.example.blooddonationapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DonateActivityTest {
    @Rule
    public ActivityScenarioRule<RequestActivity> activityRule =
            new ActivityScenarioRule(RequestActivity.class);

    @Test
    public void myClassMethod_ReturnsTrue() {

        onView(withId(R.id.reason)).perform(typeText("text note"), closeSoftKeyboard());
        onView(withId(R.id.amount)).perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.checkBox)).perform(click());
        onView(withId(R.id.BloodType1)).perform(click());


    }
}
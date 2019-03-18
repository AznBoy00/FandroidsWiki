package org.wikipedia.espresso.notifications;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;
import org.wikipedia.notifications.NotificationSchedulerActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class NotificationSchedulerActivityTest {
    @Rule
    public ActivityTestRule<NotificationSchedulerActivity> activityTestRule = new ActivityTestRule<>(NotificationSchedulerActivity.class);

    @Test
    public void testTimePicker(){
        onView(withId(R.id.btnNotify)).perform(click());
        int hour = 10;
        int minutes = 59;

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(hour, minutes));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.TextView_Time)).check(matches(withText(hour + ":" + minutes)));
    }

    @Test
    public void testDisableNotification() {
        onView(withId(R.id.button_cancel_notification)).perform(click());
    }

}

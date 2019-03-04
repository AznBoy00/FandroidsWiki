package org.wikipedia.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;
import org.powermock.api.mockito.PowerMockito;

import java.util.Calendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NotificationRandomArticleTest {
    //initialize variables
    Context context;
    Intent intent;
    PendingIntent pendingIntent;
    Calendar calendar;
    AlarmManager aManager;

    @Before
    public void SetUp() {
        context = PowerMockito.mock(Context.class);
        intent = mock(Intent.class);
        pendingIntent = PowerMockito.mock(PendingIntent.class);
        calendar = mock(Calendar.class);
        aManager = PowerMockito.mock(AlarmManager.class);
    }

    /** Tests for Random Article Class **/
    @Test
    public void testAlarmManager() {
        intent = new Intent(RuntimeEnvironment.application, TimerRandomArticle.class);
        pendingIntent = PendingIntent.getBroadcast(RuntimeEnvironment.application, 0, intent, 0);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 12);
        calendar.set(android.icu.util.Calendar.MINUTE, 0);
        calendar.set(android.icu.util.Calendar.SECOND, 0);

        aManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        verify(aManager).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /** Test for Notification being created **/
    @Test
    public void testNotificationCreated() {
        intent = new Intent(RuntimeEnvironment.application, NotificationReceiver.class);
        NotificationReceiver cNotification = mock(NotificationReceiver.class);

        cNotification.createNotificationForRandomArticle(context);
        verify(cNotification).createNotificationForRandomArticle(context);

    }

}

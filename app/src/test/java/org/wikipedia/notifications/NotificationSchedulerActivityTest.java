package org.wikipedia.notifications;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TimePicker;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.robolectric.RuntimeEnvironment;
import org.powermock.api.mockito.PowerMockito;

import java.util.Calendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

public class NotificationSchedulerActivityTest {

    private Context context;
    Intent intent;
    PendingIntent pendingIntent;
    Calendar calendar;
    TimePickerDialog tpd;
    TimePicker tp;
    AlarmManager aManager;
    View view;

    @Before
    public void SetUp() {
        context = PowerMockito.mock(Context.class);
        intent = mock(Intent.class);
        pendingIntent = PowerMockito.mock(PendingIntent.class);
        calendar = mock(Calendar.class);
        tpd = mock(TimePickerDialog.class);
        tp = mock(TimePicker.class);
        aManager = PowerMockito.mock(AlarmManager.class);
        view = mock(View.class);
    }

    @Test
    public void setAlarmTest() {

        intent = new Intent(RuntimeEnvironment.application, NotificationSchedulerActivity.class);
        pendingIntent = PendingIntent.getBroadcast(RuntimeEnvironment.application, 0, intent, 0);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 12);
        calendar.set(android.icu.util.Calendar.MINUTE, 0);
        calendar.set(android.icu.util.Calendar.SECOND, 0);

        aManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        verify(aManager).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Test
    public void unSetAlarm() {
        intent = new Intent(RuntimeEnvironment.application, NotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(RuntimeEnvironment.application, 0, intent, 0);
        aManager.cancel(pendingIntent);
        verify(aManager).cancel(pendingIntent);
    }
}

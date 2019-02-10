package org.wikipedia.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

import org.wikipedia.feed.random.RandomCardView;
import org.wikipedia.random.RandomActivity;

public class TimerRandomArticle {

    private Context context;
    private PendingIntent pendingIntent;

    public TimerRandomArticle(Context context) {
        this.context = context;
        this.pendingIntent = PendingIntent.getBroadcast(context,0,new Intent(context, NotificationRandomArticle.class),0);
    }

    public void alarmManager(){
        /*
         Make the alarm user settable in the future.
         */
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 2);
        calendar.set(Calendar.SECOND, 1);
        long daily = calendar.getTimeInMillis();
        // Set Reminder to be daily
        AlarmManager manager  = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //manager .setRepeating(AlarmManager.RTC, daily, AlarmManager.INTERVAL_DAY, pendingIntent);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}

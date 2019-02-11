package org.wikipedia.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

public class TimerRandomArticle {

    private Context context;
    private PendingIntent pendingIntent;

    public TimerRandomArticle(Context context) {
        this.context = context;
        this.pendingIntent = PendingIntent.getBroadcast(context,0,new Intent(context, NotificationRandomArticle.class),0);
    }

    public void alarmManager(){
        /*
         Make the alarm user settable in the future. By Asking for the Hour, Minute, and Second
         */
        // Set Alarm for 12:02:01 PM everyday
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 2);
        calendar.set(Calendar.SECOND, 1);
        long daily = calendar.getTimeInMillis();
        // Set Reminder to be daily
        AlarmManager manager  = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}

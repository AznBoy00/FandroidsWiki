package org.wikipedia.notifications;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.widget.TextView;
import android.widget.Toast;

import org.wikipedia.R;
import org.wikipedia.activity.BaseActivity;
import org.wikipedia.citation.CitationActivity;

import java.util.Calendar;

public class NotificationSchedulerActivity extends BaseActivity {
    TextView TextView_Time;
    private String notificationOff = "Current Daily Notification Time: OFF";
    private String timerSet = "Timer Set.";
    private String setTime1, setTime2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_scheduler);
        TextView_Time= findViewById(R.id.TextView_Time);

        AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE );
//        if (alarmManager.getNextAlarmClock() == null) {
//            TextView_Time.setText("Current Daily Notification Time: OFF");
//        } else {
//            TextView_Time.setText("Current Daily Notification Time: " + alarmManager.getNextAlarmClock());
//        }
    }

    // Method that shows the TimePickerDialog when the button is clicked
    public void btn_set_alarm(View view) {

        showDialog(1);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        //Display the time picker as a Clock
        // TimePickerDialog tpd = new TimePickerDialog(NotificationSchedulerActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK, TimeMap, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);

        // Display the timePicker as a spinner
        TimePickerDialog tpd = new TimePickerDialog(NotificationSchedulerActivity.this,android.R.style.Theme_Holo_Dialog, TimeMap, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        if (id == 1) {
            return tpd;
        }
        return null;

    }

    protected TimePickerDialog.OnTimeSetListener TimeMap =
        new TimePickerDialog.OnTimeSetListener() {

            // Sets the time of the notification via the TimePickerDialog and sets the alarmManager to repeat the notifications everyday
            @Override
            public void onTimeSet(TimePicker TimeP, int hourOfDay, int minute) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                Intent alertIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
                AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE );

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY , PendingIntent.getBroadcast(getApplicationContext(), 0, alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT ));

                setTime1 = hourOfDay + ":0" + minute;
                setTime1 = hourOfDay + ":" + minute;

                //Display the specified notification time
                if(minute<10) {
                    TextView_Time.setText(setTime1);
                } else {
                    TextView_Time.setText(setTime1);
                }
                Toast.makeText(NotificationSchedulerActivity.this, timerSet, Toast.LENGTH_SHORT).show();
            }
        };

    public void unSetAlarm(View view){
        Intent alertIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, alertIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE );
        alarmManager.cancel(sender);
        TextView_Time.setText(notificationOff);
        Toast.makeText(NotificationSchedulerActivity.this, notificationOff, Toast.LENGTH_SHORT).show();
    }
}

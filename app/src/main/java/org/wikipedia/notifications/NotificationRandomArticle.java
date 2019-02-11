package org.wikipedia.notifications;

import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.widget.Toast;

import com.allyants.notifyme.NotifyMe;

import org.wikipedia.R;
import org.wikipedia.random.RandomActivity;
import org.wikipedia.R;

/* This class will display a Notification of a Random Article */

public class NotificationRandomArticle extends BroadcastReceiver {
    //public class NotificationRandomArticle extends NotificationActivity implements NotificationItemActionsDialog.Callback {
    private static final String CHANNEL_ID = "RANDOM_ARTICLE_CHANNEL";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm running", Toast.LENGTH_SHORT).show();
        Intent recieveIntent = new Intent(context, RandomActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, recieveIntent, 0);
        createNotificationForRandomArticle(context);

        //NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createNotificationForRandomArticle(Context context) {
        String etTitle = "Hey!, Want to read something interesting";
        String etContent = "Click here to read a random article on Wikipedia";
        NotifyMe notifyMe = new NotifyMe.Builder(context)
                .title(etTitle)
                .content(etContent)
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                //.time(now)
                .addAction(new Intent(),"Snooze",false)
                .key("test")
                .addAction(new Intent(),"Dismiss",true,false)
                .addAction(new Intent(context,RandomActivity.class),"Done")
                .large_icon(R.mipmap.launcher)
                .build();
    }

}
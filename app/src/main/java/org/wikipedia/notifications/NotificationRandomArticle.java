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

        //setContentView(R.layout.view_main_drawer);
//        displayRandomNotification(context, pendingIntent);

//        Button TestRnd = (Button) findViewById(R.id.noti_test);
//        TestRnd.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v){
//                displayRandomNotification(context, pendingIntent);
//            }
//        });
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("Cool stuff homie")
                        .setContentText("Notification works")
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, mBuilder.build());

    }


//    public void displayRandomNotification(){
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this,CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//                .setContentTitle("Cool stuff homie")
//                .setContentText("Notification works")
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        //NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
//        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(1, mBuilder.build());
//    }


//    public void onCreat(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_main_drawer);
//
//        findViewById(R.id.noti_test).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                displayRandomNotification();
//            }
//        });
//    }

}
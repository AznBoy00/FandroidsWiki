package org.wikipedia.notifications;

import org.wikipedia.activity.BaseActivity;

public class NotificationSchedulerActivity extends BaseActivity {
    TextView TextView_Time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_scheduler);
        TextView_Time= findViewById(R.id.TextView_Time);

    }

    // Method that shows the TimePickerDialog when the button is clicked
    public void btn_set_alarm(View view) {

        showDialog(1);
    }

}

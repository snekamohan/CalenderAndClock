package com.example.calenderandclock;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

public class CalendarActivity extends AppCompatActivity {
    TextView myDate;
    EditText email,email_msg;
    CalendarView calendarView;
    String date;
    Button notify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        notify = (Button) findViewById(R.id.notify_date);
        email = (EditText) findViewById(R.id.mailTo);
        email_msg = (EditText) findViewById(R.id.email_msg);

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotifyMe();
            }
        });
        calendarView = (CalendarView) findViewById(R.id.calendar);
        myDate = (TextView) findViewById(R.id.myDate);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date = i2 + "-" + (i1+1) + "-" + i;
                myDate.setText(date);
            }
        });
    }

    public void NotifyMe(){
        String id = "my_channel_id_01";
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = manager.getNotificationChannel(id);
            if(channel == null){
                channel = new NotificationChannel(id, "Channel Title",NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("[channel description]");
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 1000, 200,340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);
            }
        }
        Intent intent = new Intent(this,NotifyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id)
                .setSmallIcon(R.drawable.ic_baseline_message_24)
                .setContentText("You have selected: "+ date)
                .setContentTitle("Date Selected")
                .setAutoCancel(false);
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
        m.notify(1,builder.build());
        String recipientList = email.getText().toString();
        String email_txt = email_msg.getText().toString();
        String[] recipients = recipientList.split(",");
        String subject = "Reminder";
        String message = "You have set reminder on "+ date + " for "+email_txt;
        Intent intent1 = new Intent(Intent.ACTION_SENDTO);
        intent1.setData(Uri.parse("mailto:"));
        intent1.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent1.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent1.putExtra(Intent.EXTRA_TEXT, message);
//        intent1.setType("message/rfc822");
        startActivity(Intent.createChooser(intent1, "Choose an email client"));
    }
}
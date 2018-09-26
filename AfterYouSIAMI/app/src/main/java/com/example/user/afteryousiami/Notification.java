package com.example.user.afteryousiami;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class Notification extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Button btn = (Button) findViewById(R.id.btnShowNotification);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationcall();
            }

        });
    }


        public void notificationcall(){

            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this).setDefaults(NotificationCompat.DEFAULT_ALL).setSmallIcon(R.drawable.logo).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo)).setContentTitle("Notification Call").setContentText("Test");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1,notificationBuilder.build());



        }
    }


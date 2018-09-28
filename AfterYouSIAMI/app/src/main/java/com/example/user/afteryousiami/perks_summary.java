package com.example.user.afteryousiami;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.afteryousiami.objects.Perks;

import java.util.List;

public class perks_summary extends Activity {

    List<Perks> addedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perks_summary);
        addedList = (List<Perks>) getIntent().getSerializableExtra("AddedList");

        initPerksUI();


        Button btn = (Button) findViewById(R.id.btn_confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationcall();
            }

        });
    }

    private void initPerksUI(){


    }

    /***
     * for notification
     */
    private void notificationcall() {
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this).setDefaults(NotificationCompat.DEFAULT_ALL).setSmallIcon(R.drawable.logo).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo)).setContentTitle("Notification Call").setContentText("Test");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private static void printDebug(String message) {
        Log.d("perks_summary.java", message);
    }

    private static void printError(Exception e) {
        Log.e("perks_summary.java", "error", e);
    }

}

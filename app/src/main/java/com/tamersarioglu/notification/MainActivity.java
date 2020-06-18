package com.tamersarioglu.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonBildir = findViewById(R.id.buttonBildir);
        buttonBildir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bildirimGecikmesi();
            }
        });
    }

    public void checkSystem() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(MainActivity.this, DigerActivity.class);
        PendingIntent gidilecekIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String kanalId = "KanalId";
            String kanalAd = "KanalAd";
            String kanalTanim = "kanalTanim";
            int kanalOncelik = NotificationManager.IMPORTANCE_HIGH;

            assert notificationManager != null;
            NotificationChannel kanal = notificationManager.getNotificationChannel(kanalId);
            if (kanal == null) {
                kanal = new NotificationChannel(kanalId, kanalAd, kanalOncelik);
                kanal.setDescription(kanalTanim);
                notificationManager.createNotificationChannel(kanal);
            }
            builder = new NotificationCompat.Builder(this, kanalId);
            builder.setContentTitle("Başlık");
            builder.setContentText("İçerik");
            builder.setSmallIcon(R.drawable.ic_baseline_add_a_photo_24);
            builder.setAutoCancel(true);
            builder.setContentIntent(gidilecekIntent);

        } else {
            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Başlık");
            builder.setContentText("İçerik");
            builder.setSmallIcon(R.drawable.ic_baseline_add_a_photo_24);
            builder.setAutoCancel(true);
            builder.setContentIntent(gidilecekIntent);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        assert notificationManager != null;
        notificationManager.notify(1, builder.build());

    }

    public void bildirimGecikmesi() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(MainActivity.this, DigerActivity.class);
        PendingIntent gidilecekIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String kanalId = "KanalId";
            String kanalAd = "KanalAd";
            String kanalTanim = "kanalTanim";
            int kanalOncelik = NotificationManager.IMPORTANCE_HIGH;

            assert notificationManager != null;
            NotificationChannel kanal = notificationManager.getNotificationChannel(kanalId);
            if (kanal == null) {
                kanal = new NotificationChannel(kanalId, kanalAd, kanalOncelik);
                kanal.setDescription(kanalTanim);
                notificationManager.createNotificationChannel(kanal);
            }
            builder = new NotificationCompat.Builder(this, kanalId);
            builder.setContentTitle("Başlık");
            builder.setContentText("İçerik");
            builder.setSmallIcon(R.drawable.ic_baseline_add_a_photo_24);
            builder.setAutoCancel(true);
            builder.setContentIntent(gidilecekIntent);

        } else {
            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Başlık");
            builder.setContentText("İçerik");
            builder.setSmallIcon(R.drawable.ic_baseline_add_a_photo_24);
            builder.setAutoCancel(true);
            builder.setContentIntent(gidilecekIntent);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        Intent broadCastIntent = new Intent(MainActivity.this, BildirimYakala.class);
        broadCastIntent.putExtra("nesne", builder.build());

        PendingIntent gidilenBroadcast = PendingIntent.getBroadcast(this, 0,
                broadCastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        long gecikme = SystemClock.elapsedRealtime() + 5000;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, calendar.getTimeInMillis(), 1000 * 60 * 20, gidilenBroadcast);
    }
}
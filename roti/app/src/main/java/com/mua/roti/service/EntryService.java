package com.mua.roti.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.mua.roti.R;

public class EntryService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService(new Intent(EntryService.this, NotificationListener.class));
        createNotification();
        return Service.START_STICKY;
    }

    private void createNotification(){
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId
                = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                ? getNotificationChannel(notificationManager)
                : "";
        NotificationCompat.Builder notificationBuilder
                = new NotificationCompat.Builder(this, channelId);
        Notification notification
                = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .build();

        startForeground(1, notification);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String getNotificationChannel(NotificationManager notificationManager){
        String channelId = "roti-notification";
        String channelName = getResources().getString(R.string.app_name);
        NotificationChannel channel
                = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationManager.createNotificationChannel(channel);
        return channelId;
    }

}

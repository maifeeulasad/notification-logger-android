package com.mua.roti.service;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationListener extends NotificationListenerService {


    @Override
    public void onListenerConnected() {
        super.onListenerConnected();

        Log.d("d--mua", "service connected");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();

        Log.d("d--mua", "service disconnected");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        Bundle bundle = notification.extras;

        String title = bundle.getString(NotificationCompat.EXTRA_TITLE);
        String text = bundle.getString(NotificationCompat.EXTRA_TEXT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            Log.d("d--mua", "key :  " + sbn.getKey());
        }
        Log.d("d--mua", "title : " + title);
        Log.d("d--mua", "text : " + text);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}
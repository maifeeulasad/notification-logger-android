package com.mua.roti.service;

import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.mua.roti.model.NotificationEntry;
import com.mua.roti.model.builder.NotificationEntryBuilder;
import com.mua.roti.repository.NotificationEntryRepository;

import java.sql.Date;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationListener extends NotificationListenerService {

    private final NotificationEntryRepository notificationEntryRepository;


    public NotificationListener(){
        notificationEntryRepository = new NotificationEntryRepository(getApplication());
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        Bundle bundle = notification.extras;

        String title = bundle.getString(NotificationCompat.EXTRA_TITLE);
        String text = bundle.getString(NotificationCompat.EXTRA_TEXT);

        String key = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            key = sbn.getKey();
        }

        NotificationEntry notificationEntry
                = new NotificationEntryBuilder()
                .setKey(key)
                .setTitle(title)
                .setText(text)
                .build();

        notificationEntryRepository.insert(notificationEntry);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}
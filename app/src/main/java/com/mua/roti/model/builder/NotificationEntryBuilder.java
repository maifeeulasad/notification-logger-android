package com.mua.roti.model.builder;

import com.mua.roti.model.NotificationEntry;

import java.sql.Date;

public class NotificationEntryBuilder {

    NotificationEntry notificationEntry;
    
    public NotificationEntryBuilder(){
        notificationEntry = new NotificationEntry();
    }

    public NotificationEntryBuilder setKey(String key) {
        notificationEntry.setKey(key);
        return this;
    }
    
    public NotificationEntryBuilder setTitle(String title) {
        notificationEntry.setTitle(title);
        return this;
    }
    
    public NotificationEntryBuilder setText(String text) {
        notificationEntry.setText(text);
        return this;
    }

    public NotificationEntry build(){
        return notificationEntry;
    }

}

package com.mua.roti.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.mua.roti.converter.DateConverter;

import java.sql.Date;

@Entity(tableName = "notification_entry")
@TypeConverters(DateConverter.class)
public class NotificationEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notification_entry_id")
    private Long notificationEntryId;

    @ColumnInfo(name = "key")
    private String key;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "timeStamp")
    private Date timeStamp;

    public Long getNotificationEntryId() {
        return notificationEntryId;
    }

    public void setNotificationEntryId(Long notificationEntryId) {
        this.notificationEntryId = notificationEntryId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
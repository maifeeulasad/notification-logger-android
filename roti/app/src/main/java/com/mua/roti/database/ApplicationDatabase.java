package com.mua.roti.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mua.roti.dao.NotificationEntryDao;
import com.mua.roti.model.NotificationEntry;

@Database(entities = {NotificationEntry.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {
    private static volatile ApplicationDatabase INSTANCE;

    public abstract NotificationEntryDao notificationEntryDao();

    public static ApplicationDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized(ApplicationDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ApplicationDatabase.class, "roti.db").build();
                }
            }
        }
        return INSTANCE;
    }
}
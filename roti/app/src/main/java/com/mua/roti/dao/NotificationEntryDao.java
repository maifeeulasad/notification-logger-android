package com.mua.roti.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mua.roti.model.NotificationEntry;

import java.util.List;

@Dao
public interface NotificationEntryDao {

    @Query("SELECT * FROM NOTIFICATION_ENTRY")
    LiveData<List<NotificationEntry>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NotificationEntry notificationEntry);
}
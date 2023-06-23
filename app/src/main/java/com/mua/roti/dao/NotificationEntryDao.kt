package com.mua.roti.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mua.roti.model.NotificationEntry

@Dao
interface NotificationEntryDao {
    @get:Query("SELECT * FROM NOTIFICATION_ENTRY")
    val all: LiveData<List<NotificationEntry?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notificationEntry: NotificationEntry?)
}
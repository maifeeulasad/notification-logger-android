package com.mua.roti.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.mua.roti.dao.NotificationEntryDao
import com.mua.roti.model.NotificationEntry

@Database(entities = [NotificationEntry::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun notificationEntryDao(): NotificationEntryDao?

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): ApplicationDatabase? {
            if (INSTANCE == null) {
                synchronized(ApplicationDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(
                            context.applicationContext,
                            ApplicationDatabase::class.java, "roti.db"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
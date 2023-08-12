package com.mua.roti.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mua.roti.dao.NotificationEntryDao
import com.mua.roti.model.NotificationEntry

@Database(entities = [NotificationEntry::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun notificationEntryDao(): NotificationEntryDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: createDatabase(context).also { INSTANCE = it }
            }
        }

        private fun createDatabase(context: Context): ApplicationDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ApplicationDatabase::class.java, "roti.db"
            ).build()
        }
    }
}
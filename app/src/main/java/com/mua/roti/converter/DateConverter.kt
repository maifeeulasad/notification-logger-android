package com.mua.roti.converter

import androidx.room.TypeConverter
import java.sql.Date

object DateConverter {
    @JvmStatic
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @JvmStatic
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
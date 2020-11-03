package com.ismin.android.data.database

import androidx.room.TypeConverter
import org.joda.time.DateTime

class Converters {  // Pour Room
    @TypeConverter
    fun fromTimestamp(value: String?): DateTime? {
        return value?.let { DateTime(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: DateTime?): String? {
        return date?.toString()
    }
}
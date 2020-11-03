package com.ismin.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ismin.android.data.models.BookModel

@Database(
    entities = [BookModel::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun bookDao(): BookDao
}

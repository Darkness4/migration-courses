package com.ismin.android.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ismin.android.data.models.BookModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(books: List<BookModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg book: BookModel)

    @Query("DELETE FROM Book")
    suspend fun clear()

    @Delete
    suspend fun deleteOne(book: BookModel)

    @Query("SELECT * FROM Book WHERE title=:title")
    suspend fun findById(title: String): BookModel?

    @Query("SELECT * FROM Book WHERE author=:author")
    suspend fun find(author: String): List<BookModel>

    @Query("SELECT * FROM Book")
    suspend fun find(): List<BookModel>

    @Query("SELECT * FROM Book")
    fun watch(): Flow<List<BookModel>>

    @Query("SELECT COUNT(title) FROM Book")
    suspend fun count(): Int
}

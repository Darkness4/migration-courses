package com.ismin.android.domain.repositories

import com.ismin.android.core.result.Result
import com.ismin.android.domain.entities.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun create(book: Book): Result<Unit>
    suspend fun findById(title: String): Result<Book>
    suspend fun find(): Result<List<Book>>
    suspend fun find(author: String): Result<List<Book>>
    suspend fun count(): Result<Int>
    suspend fun clear(): Result<Unit>
    suspend fun deleteById(title: String): Result<Book>
    fun watch(): Flow<Result<List<Book>>>
}

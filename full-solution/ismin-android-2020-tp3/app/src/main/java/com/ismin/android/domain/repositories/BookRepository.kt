package com.ismin.android.domain.repositories

import com.ismin.android.core.result.Result
import com.ismin.android.domain.entities.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun addBook(book: Book): Result<Unit>
    suspend fun getBook(title: String): Result<Book>
    suspend fun getAllBooks(): Result<List<Book>>
    suspend fun getBooksOf(author: String): Result<List<Book>>
    suspend fun getTotalNumberOfBooks(): Result<Int>
    suspend fun clearAllBooks(): Result<Unit>
    fun watchAllBooks(): Flow<Result<List<Book>>>
}

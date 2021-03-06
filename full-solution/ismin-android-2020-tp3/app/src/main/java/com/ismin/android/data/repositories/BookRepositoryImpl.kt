package com.ismin.android.data.repositories

import com.ismin.android.core.result.Result
import com.ismin.android.data.database.BookDao
import com.ismin.android.data.datasources.BookshelfDataSource
import com.ismin.android.data.models.BookModel
import com.ismin.android.domain.entities.Book
import com.ismin.android.domain.repositories.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookDao: BookDao,
    private val bookshelfDataSource: BookshelfDataSource
) : BookRepository {
    override suspend fun create(book: Book): Result<Unit> {
        return withContext(Dispatchers.Default) {
            bookshelfDataSource.addBook(book.asModel())
            bookDao.insert(book.asModel())
            Result.Success(Unit)
        }
    }

    override suspend fun findById(title: String): Result<Book> {
        return withContext(Dispatchers.Default) {
            try {
                val book = bookshelfDataSource.findOneBook(title)
                book?.let {
                    bookDao.insert(book)
                    Result.Success(book.asEntity())
                } ?: Result.Failure(Exception("Element not found."))
            } catch (e: Throwable) {
                Result.Failure(e)
            }
        }
    }

    override suspend fun find(): Result<List<Book>> {
        return withContext(Dispatchers.Default) {
            try {
                val paginatedDto = bookshelfDataSource.findBooks(null)
                bookDao.insert(paginatedDto.results)
                Result.Success(paginatedDto.results.map { it.asEntity() })
            } catch (e: Throwable) {
                Result.Failure(e)
            }
        }
    }

    override suspend fun find(author: String): Result<List<Book>> {
        return withContext(Dispatchers.Default) {
            try {
                val paginatedDto = bookshelfDataSource.findBooks(author)
                bookDao.insert(paginatedDto.results)
                Result.Success(paginatedDto.results.map { it.asEntity() })
            } catch (e: Throwable) {
                Result.Failure(e)
            }
        }
    }

    override suspend fun count(): Result<Int> {
        return Result.Success(bookDao.count())
    }

    override suspend fun clear(): Result<Unit> {
        return try {
            bookshelfDataSource.clearAllBooks()
            bookDao.clear()
            Result.Success(Unit)
        } catch (e: Throwable) {
            Result.Failure(e)
        }
    }

    override suspend fun deleteById(title: String): Result<Book> {
        return try {
            val bookModel = bookshelfDataSource.deleteOneBook(title)
            bookModel?.let {
                bookDao.deleteOne(bookModel)
                return Result.Success(bookModel.asEntity())
            } ?: Result.Failure(Exception("Element not found."))
        } catch (e: Throwable) {
            Result.Failure(e)
        }
    }

    override fun watch(): Flow<Result<List<Book>>> {
        return bookDao.watch()
            .map<List<BookModel>, Result<List<Book>>> { Result.Success(it.map { model -> model.asEntity() }) }
            .catch { emit(Result.Failure(it)) }
            .flowOn(Dispatchers.Default)
    }
}

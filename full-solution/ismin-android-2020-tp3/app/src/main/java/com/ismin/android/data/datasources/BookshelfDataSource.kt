package com.ismin.android.data.datasources

import com.ismin.android.data.models.BookModel
import com.ismin.android.data.models.PaginatedDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfDataSource {
    companion object {
        const val BASE_URL = "https://bookshelf-marc-nguyen.cleverapps.io/"
        const val CONTENT_TYPE = "application/json; charset=utf-8"
    }

    @POST("books")
    suspend fun addBook(@Body book: BookModel)

    @GET("books")
    suspend fun findBooks(@Query("author") author: String?): PaginatedDto<BookModel>

    @GET("books/{title}")
    suspend fun findOneBook(@Path("title") title: String): BookModel

    @DELETE("books/{title}")
    suspend fun deleteOneBook(@Path("title") title: String): BookModel?

    @DELETE("books")
    suspend fun clearAllBooks()
}

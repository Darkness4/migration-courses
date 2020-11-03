package com.ismin.android.di

import android.content.Context
import androidx.room.Room
import com.ismin.android.data.database.BookDao
import com.ismin.android.data.database.Database
import com.ismin.android.data.datasources.BookshelfDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "app.db"
        ).build()
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideBookshelfDataSource(): BookshelfDataSource {
        return Retrofit.Builder()
            .baseUrl(BookshelfDataSource.BASE_URL)
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }.asConverterFactory(
                    BookshelfDataSource.CONTENT_TYPE.toMediaType()
                )
            )
            .build()
            .create(BookshelfDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideBookDao(database: Database): BookDao {
        return database.bookDao()
    }
}

package com.ismin.android.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ismin.android.core.mappers.EntityMappable
import com.ismin.android.core.serializers.DateTimeSerializer
import com.ismin.android.domain.entities.Book
import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Entity(tableName = "Book")
@Serializable
data class BookModel(
    @PrimaryKey
    val title: String,
    val author: String,
    @Serializable(with = DateTimeSerializer::class)
    val date: DateTime
) : EntityMappable<Book> {
    override fun asEntity() = Book(title, author, date)
}

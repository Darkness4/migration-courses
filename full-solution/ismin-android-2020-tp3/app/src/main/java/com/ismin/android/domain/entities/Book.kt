package com.ismin.android.domain.entities

import com.ismin.android.core.mappers.ModelMappable
import com.ismin.android.data.models.BookModel
import org.joda.time.DateTime
import java.io.Serializable

data class Book(val title: String, val author: String, val date: DateTime) :
    Serializable,
    ModelMappable<BookModel> {
    override fun asModel() = BookModel(title, author, date)
}

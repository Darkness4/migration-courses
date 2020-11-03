package com.ismin.android.ui.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ismin.android.core.result.Result
import com.ismin.android.domain.entities.Book
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@BindingAdapter("books")
fun bindBookAdapter(
    recyclerView: RecyclerView,
    books: Result<List<Book>>?
) {
    val adapter = recyclerView.adapter as BookAdapter
    books?.doOnSuccess { adapter.submitList(it) }
}

@BindingAdapter("date")
fun bindDate(
    textView: TextView,
    date: DateTime?
) {
    val fmt = DateTimeFormat.forPattern("d MMM y")
    date?.toString(fmt)?.let { textView.text = it }
}
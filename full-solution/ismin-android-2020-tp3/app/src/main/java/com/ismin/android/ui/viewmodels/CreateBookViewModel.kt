package com.ismin.android.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismin.android.domain.entities.Book
import org.joda.time.DateTime

class CreateBookViewModel : ViewModel() {
    val author = MutableLiveData("")
    val title = MutableLiveData("")
    val date = MutableLiveData(DateTime.now())

    private val _saveBook = MutableLiveData<Unit?>(null)
    val saveBook: LiveData<Unit?>
        get() = _saveBook

    fun saveBook() {
        _saveBook.value = Unit
    }

    fun saveBookDone() {
        _saveBook.value = null
    }

    private val _showDatePicker = MutableLiveData<Unit?>()
    val showDatePicker: LiveData<Unit?>
        get() = _showDatePicker

    fun showDatePicker() {
        _showDatePicker.value = Unit
    }

    fun showDatePickerDone() {
        _showDatePicker.value = null
    }

    fun toBook(): Book? {
        val title = title.value
        val author = author.value
        val date = date.value
        return if (title != null && author != null && date != null) {
            Book(title, author, date)
        } else {
            null
        }
    }
}

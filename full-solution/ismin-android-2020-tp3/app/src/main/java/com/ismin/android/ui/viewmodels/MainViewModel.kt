package com.ismin.android.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ismin.android.core.result.Result
import com.ismin.android.domain.entities.Book
import com.ismin.android.domain.repositories.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val bookRepository: BookRepository) : ViewModel() {
    val books = bookRepository.watch()
        .asLiveData(viewModelScope.coroutineContext + Dispatchers.Default)

    private val _networkStatus = MutableLiveData<Result<Unit>>()
    val networkStatus: LiveData<Result<Unit>>
        get() = _networkStatus

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch(Dispatchers.Main) {
            _networkStatus.value = bookRepository.find().map { Unit }
            manualRefreshDone()
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch(Dispatchers.Main) {
            _networkStatus.value = bookRepository.create(book)
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.Main) {
            _networkStatus.value = bookRepository.clear()
        }
    }

    private val _goToCreation = MutableLiveData<Unit?>(null)
    val goToCreation: LiveData<Unit?>
        get() = _goToCreation

    fun goToCreation() {
        _goToCreation.value = Unit
    }

    fun goToCreationDone() {
        _goToCreation.value = null
    }

    fun removeBook(book: Book) {
        viewModelScope.launch {
            _networkStatus.value = bookRepository.deleteById(book.title).map{ Unit }
        }
    }

    private val _manualRefresh = MutableLiveData(false)
    val manualRefresh: LiveData<Boolean>
        get() = _manualRefresh

    fun manualRefresh() {
        _manualRefresh.value = true
        refresh()
    }

    private fun manualRefreshDone() {
        _manualRefresh.value = false
    }

    class Factory(
        private val bookRepository: BookRepository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(bookRepository) as T
        }
    }
}

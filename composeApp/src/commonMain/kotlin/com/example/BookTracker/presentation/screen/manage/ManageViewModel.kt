package com.example.BookTracker.presentation.screen.manage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BookTracker.data.BookDatabase
import com.example.BookTracker.domain.Book
import com.example.BookTracker.navigation.BOOK_ID_ARG
import kotlinx.coroutines.launch

const val IMAGE_URL = "https://ayine.com.br/wp-content/uploads/2022/03/Miolo-diagonal-O-livro-dos-amigos-site-680x476.png"

class ManageViewModel(
    private val database: BookDatabase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val selectedBookId = savedStateHandle.get<Int>(BOOK_ID_ARG) ?: -1
    var imageField = mutableStateOf(IMAGE_URL)
    var titleField = mutableStateOf("")
    var summaryField = mutableStateOf("")
    var categoryField = mutableStateOf("")
    var tagsField = mutableStateOf("")
    var authorField = mutableStateOf("")

    init {
        viewModelScope.launch {
            if (selectedBookId != -1) {
                val selectedBook = database.bookDao()
                    .getBookById(selectedBookId)
                imageField.value = selectedBook.image
                titleField.value = selectedBook.title
                summaryField.value = selectedBook.summary
                categoryField.value = selectedBook.category
                tagsField.value = selectedBook.tags.joinToString()
                authorField.value = selectedBook.author
            }
        }
    }

    fun insertBook(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (
                    titleField.value.isNotEmpty() &&
                    summaryField.value.isNotEmpty() &&
                    categoryField.value.isNotEmpty() &&
                    tagsField.value.isNotEmpty() &&
                    authorField.value.isNotEmpty()
                ) {
                    database.bookDao()
                        .insertBook(
                            book = Book(
                                image = imageField.value,
                                title = titleField.value,
                                summary = summaryField.value,
                                category = categoryField.value,
                                tags = tagsField.value.split(","),
                                author = authorField.value,
                                isFavorite = false
                            ),
                        )
                    onSuccess()
                } else {
                    onError("Os campos não podem ficar em branco.")
                }
            } catch (e: Exception) {
                onError(e.toString())
            }
        }
    }

    fun updateBook(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (titleField.value.isNotEmpty() &&
                    summaryField.value.isNotEmpty() &&
                    categoryField.value.isNotEmpty() &&
                    tagsField.value.isNotEmpty() &&
                    authorField.value.isNotEmpty()
                ) {
                    database.bookDao()
                        .updateBook(
                            book = Book(
                                _id = selectedBookId,
                                image = imageField.value,
                                title = titleField.value,
                                summary = summaryField.value,
                                category = categoryField.value,
                                tags = tagsField.value.split(","),
                                author = authorField.value,
                                isFavorite = database.bookDao()
                                    .getBookById(selectedBookId).isFavorite
                            )
                        )
                    onSuccess()
                } else {
                    onError("Os campos não podem ficar em branco.")
                }
            } catch (e: Exception) {
                onError(e.toString())
            }
        }
    }
}
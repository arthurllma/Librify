package br.unifor.librify.ui.features.bookdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import br.unifor.librify.data.repository.ActivityRepository
import br.unifor.librify.domain.model.Book
import br.unifor.librify.domain.model.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    // 1. Recovering the 'bookId' from the route parameters
    private val bookId: String = checkNotNull(savedStateHandle["bookId"])

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book.asStateFlow()

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    init {
        loadBookDetails()
    }

    private fun loadBookDetails() {
        // 2. Using the ID to find the book in our Repository Singleton
        _book.value = ActivityRepository.getBookById(bookId)
        
        // Simulating reviews for the specific book
        _reviews.value = listOf(
            Review("1", "Arthur Lima", 5, "Obra prima! Indispensável.", "10/05/2024"),
            Review("2", "Maria Santos", 4, "Impactante e existencialista.", "12/05/2024"),
            Review("3", "Carlos Oliveira", 5, "A escrita de Kafka é única.", "15/05/2024")
        )
    }

    fun onReserveClick() {
        _book.value?.let { currentBook ->
            ActivityRepository.reserveBook(currentBook)
        }
    }
}

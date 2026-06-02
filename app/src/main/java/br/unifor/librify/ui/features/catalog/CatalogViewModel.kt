package br.unifor.librify.ui.features.catalog

import androidx.lifecycle.ViewModel
import br.unifor.librify.data.repository.ActivityRepository
import br.unifor.librify.domain.model.Book
import kotlinx.coroutines.flow.*

class CatalogViewModel : ViewModel() {
    private val allBooks = ActivityRepository.allBooks

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val categories = listOf("Todos", "Engenharia", "Literatura", "Computação", "Psicologia", "História")

    val filteredBooks: StateFlow<List<Book>> = combine(_searchQuery, _selectedCategory) { query, category ->
        allBooks.filter { book ->
            val matchesQuery = book.title.contains(query, ignoreCase = true) || 
                               book.author.contains(query, ignoreCase = true)
            val matchesCategory = category == "Todos" || book.category == category
            matchesQuery && matchesCategory
        }
    }.stateIn(
        scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Default),
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = allBooks
    )

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onCategorySelect(category: String) {
        _selectedCategory.value = category
    }
}

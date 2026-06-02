package br.unifor.librify.ui.features.publish

import androidx.lifecycle.ViewModel
import br.unifor.librify.data.repository.ActivityRepository
import br.unifor.librify.domain.model.Publication
import br.unifor.librify.domain.model.PublicationStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class PublishViewModel : ViewModel() {
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _author = MutableStateFlow("")
    val author: StateFlow<String> = _author.asStateFlow()

    private val _genre = MutableStateFlow("")
    val genre: StateFlow<String> = _genre.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    val myPublications: StateFlow<List<Publication>> = ActivityRepository.publications

    fun onTitleChange(value: String) { _title.value = value }
    fun onAuthorChange(value: String) { _author.value = value }
    fun onGenreChange(value: String) { _genre.value = value }
    fun onDescriptionChange(value: String) { _description.value = value }

    fun submitPublication() {
        if (_title.value.isBlank()) return

        val newPub = Publication(
            id = UUID.randomUUID().toString(),
            title = _title.value,
            author = _author.value,
            genre = _genre.value,
            status = PublicationStatus.PENDING,
            date = "02/06/2024"
        )
        
        ActivityRepository.submitPublication(newPub)
        
        // Reset fields
        _title.value = ""
        _author.value = ""
        _genre.value = ""
        _description.value = ""
    }
}

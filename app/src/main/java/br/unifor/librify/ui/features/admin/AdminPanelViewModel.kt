package br.unifor.librify.ui.features.admin

import androidx.lifecycle.ViewModel
import br.unifor.librify.data.repository.ActivityRepository
import br.unifor.librify.domain.model.Publication
import br.unifor.librify.domain.model.PublicationStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AdminPanelViewModel : ViewModel() {
    
    val pendingPublications: StateFlow<List<Publication>> = ActivityRepository.publications
        .map { list -> list.filter { it.status == PublicationStatus.PENDING } }
        .stateIn(
            scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun approvePublication(id: String) {
        ActivityRepository.updatePublicationStatus(id, PublicationStatus.APPROVED)
    }

    fun rejectPublication(id: String) {
        ActivityRepository.updatePublicationStatus(id, PublicationStatus.REJECTED)
    }
}

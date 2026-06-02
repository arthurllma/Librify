package br.unifor.librify.ui.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.unifor.librify.data.repository.ActivityRepository
import br.unifor.librify.domain.model.ActivityStatus
import br.unifor.librify.domain.model.Loan
import kotlinx.coroutines.flow.*

class HistoryViewModel : ViewModel() {
    
    val activities: StateFlow<List<Loan>> = ActivityRepository.activities

    fun getActiveActivities(): StateFlow<List<Loan>> {
        return activities.map { list ->
            list.filter { it.status == ActivityStatus.ACTIVE || it.status == ActivityStatus.AWAITING_PICKUP }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun getPastActivities(): StateFlow<List<Loan>> {
        return activities.map { list ->
            list.filter { it.status == ActivityStatus.FINISHED || it.status == ActivityStatus.EXPIRED }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
}

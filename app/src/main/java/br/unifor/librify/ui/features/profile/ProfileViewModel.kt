package br.unifor.librify.ui.features.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val _userName = MutableStateFlow("Admin")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userEmail = MutableStateFlow("admin@unifor.br")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    fun logout(onLogoutSuccess: () -> Unit) {
        // Here we would clear session tokens/user data in a real app
        onLogoutSuccess()
    }
}

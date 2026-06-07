package br.unifor.librify.ui.features.auth.login

import androidx.lifecycle.ViewModel
import br.unifor.librify.data.repository.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun onEmailChange(newValue: String) {
        _email.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        // Simple admin check
        if (_email.value == "admin@librify.com" && _password.value == "Admin123") {
            ActivityRepository.setAdmin(true)
        } else {
            ActivityRepository.setAdmin(false)
        }
        onSuccess()
    }

    fun fillAdminCredentials() {
        _email.value = "admin@librify.com"
        _password.value = "Admin123"
    }
}

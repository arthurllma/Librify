package br.unifor.librify.ui.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(ChatMessage("Olá! Sou o Assistente do Librify. Como posso ajudar você hoje?", false))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    fun onInputTextChange(value: String) {
        _inputText.value = value
    }

    fun sendMessage() {
        val text = _inputText.value
        if (text.isBlank()) return

        // User message
        val userMsg = ChatMessage(text, true)
        _messages.value = _messages.value + userMsg
        _inputText.value = ""

        // Simulated Assistant Response
        viewModelScope.launch {
            delay(1000)
            val responseText = when {
                text.contains("livro", ignoreCase = true) -> "Você pode encontrar diversos livros na aba de Catálogo!"
                text.contains("publicar", ignoreCase = true) -> "Para publicar uma obra, acesse a aba 'Publicar' no menu inferior."
                else -> "Entendi! Vou verificar essa informação para você."
            }
            _messages.value = _messages.value + ChatMessage(responseText, false)
        }
    }
}

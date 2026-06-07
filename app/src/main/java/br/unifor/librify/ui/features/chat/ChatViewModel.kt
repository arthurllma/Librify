package br.unifor.librify.ui.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class ChatViewModel : ViewModel() {
    private val client = OkHttpClient()
    private val apiKey = "AQ.Ab8RN6Lh4nYx_Tcqg1S2AvNIGbHLwIA3E4-ZTubwrovx3PBA_Q"
    private val apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=$apiKey"

    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(ChatMessage("Olá! Sou o Assistente do Librify. Como posso ajudar você hoje?", false))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

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

        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                val responseText = callGeminiApi(text)
                _messages.value = _messages.value + ChatMessage(responseText, false)
            } catch (e: Exception) {
                _messages.value = _messages.value + ChatMessage("Erro ao conectar com a IA: ${e.localizedMessage}", false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun callGeminiApi(prompt: String): String = withContext(Dispatchers.IO) {
        // Constructing JSON Payload
        val json = JSONObject()
        val contents = JSONArray()
        val parts = JSONArray()
        val textPart = JSONObject().put("text", "Você é o assistente virtual do Librify (UNIFOR). Responda de forma gentil: $prompt")
        
        parts.put(textPart)
        contents.put(JSONObject().put("parts", parts))
        json.put("contents", contents)

        val body = json.toString().toRequestBody("application/json".toMediaType())
        
        val request = Request.Builder()
            .url(apiUrl)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return@withContext "Ocorreu um erro na requisição: ${response.code}"
            
            val responseBody = response.body?.string() ?: return@withContext "Resposta vazia da IA."
            
            // Extracting text from Gemini response JSON
            val responseJson = JSONObject(responseBody)
            val candidates = responseJson.getJSONArray("candidates")
            val firstCandidate = candidates.getJSONObject(0)
            val content = firstCandidate.getJSONObject("content")
            val partsArray = content.getJSONArray("parts")
            
            partsArray.getJSONObject(0).getString("text")
        }
    }
}

package br.unifor.librify.data.remote

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

object GeminiService {
    private const val API_KEY = "SUA_API_KEY_AQUI"
    
    private val model = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = API_KEY,
        systemInstruction = content { 
            text("Você é o assistente virtual do Librify, um app de extensão da UNIFOR. " +
                 "Ajude os alunos com dúvidas sobre livros, regras acadêmicas de empréstimo " +
                 "e navegação no app de forma gentil e prestativa.") 
        }
    )

    suspend fun getResponse(prompt: String): String {
        return try {
            val response = model.generateContent(prompt)
            response.text ?: "Desculpe, não consegui processar sua pergunta agora."
        } catch (e: Exception) {
            "Erro de conexão com o Assistente: ${e.localizedMessage}"
        }
    }
}

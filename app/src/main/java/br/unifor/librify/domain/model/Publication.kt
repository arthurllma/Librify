package br.unifor.librify.domain.model

enum class PublicationStatus(val displayName: String) {
    PENDING("Em análise"),
    APPROVED("Aprovado"),
    REJECTED("Rejeitado")
}

data class Publication(
    val id: String,
    val title: String,
    val author: String,
    val genre: String,
    val status: PublicationStatus,
    val date: String
)

package br.unifor.librify.domain.model

enum class ActivityType {
    LOAN, RESERVATION
}

enum class ActivityStatus(val displayName: String) {
    ACTIVE("Ativo"),
    FINISHED("Devolvido"),
    EXPIRED("Expirado"),
    AWAITING_PICKUP("Aguardando busca")
}

data class Loan(
    val id: String,
    val bookTitle: String,
    val type: ActivityType,
    val status: ActivityStatus,
    val startDate: String,
    val endDate: String
)

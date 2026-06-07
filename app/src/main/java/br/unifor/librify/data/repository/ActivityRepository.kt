package br.unifor.librify.data.repository

import br.unifor.librify.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

/**
 * Singleton Repository to share data between ViewModels without a real database.
 * Ideal for prototypes and academic projects.
 */
object ActivityRepository {
    
    // Centralized Book Database for the entire app
    val allBooks = listOf(
        Book("1", "A Metamorfose", "Franz Kafka", "Literatura", 4.7, description = "A história de Gregor Samsa, um caixeiro-viajante que acorda transformado em um inseto."),
        Book("2", "Metafísica dos Costumes", "Immanuel Kant", "Filosofia", 4.8, description = "A Metafísica dos Costumes (1797) de Immanuel Kant é a obra definitiva da filosofia moral kantiana. Ela estrutura o sistema ético em duas grandes divisões: a Doutrina do Direito (que regula as leis e a justiça política) e a Doutrina da Virtude (que foca nos deveres éticos e na obrigação moral)."),
        Book("3", "1984", "George Orwell", "Literatura", 4.9, description = "Uma distopia assustadora sobre vigilância estatal e totalitarismo."),
        Book("4", "Sapiens", "Yuval Noah Harari", "História", 4.7, description = "Uma breve história da humanidade, do surgimento do Homo sapiens até hoje."),
        Book("5", "Clean Code", "Robert C. Martin", "Computação", 4.8, description = "Princípios, padrões e práticas para escrever código limpo e sustentável."),
        Book("6", "Design Patterns", "Gang of Four", "Computação", 4.6, description = "Padrões reutilizáveis para solução de problemas comuns em software orientado a objetos."),
        Book("7", "Kotlin in Action", "Dmitry Jemerov", "Computação", 4.7, description = "Guia completo para desenvolvedores que querem aprender Kotlin para produção."),
        Book("8", "Introdução à Psicologia", "Linda L. Davidoff", "Psicologia", 4.3, description = "Um panorama abrangente sobre os conceitos fundamentais da mente humana."),
        Book("9", "Cálculo A", "Diva Flemming", "Engenharia", 4.1, description = "O terror dos estudantes de engenharia: limites, derivadas e integrais."),
        Book("10", "Física I", "Halliday", "Engenharia", 4.2, description = "Fundamentos de mecânica para cursos de graduação em ciências exatas.")
    )

    // Reservation and Loan Data
    private val _activities = MutableStateFlow(
        listOf(
            Loan("101", "Dom Casmurro", ActivityType.LOAN, ActivityStatus.FINISHED, "10/05/2024", "24/05/2024"),
            Loan("102", "Sapiens", ActivityType.RESERVATION, ActivityStatus.EXPIRED, "15/05/2024", "17/05/2024")
        )
    )
    val activities: StateFlow<List<Loan>> = _activities.asStateFlow()

    // Publication Data
    private val _publications = MutableStateFlow(
        listOf(
            Publication("1", "Clean Architecture", "Uncle Bob", "Computação", PublicationStatus.PENDING, "01/06/2024"),
            Publication("2", "A Arte da Guerra", "Sun Tzu", "Estratégia", PublicationStatus.APPROVED, "25/05/2024")
        )
    )
    val publications: StateFlow<List<Publication>> = _publications.asStateFlow()

    // Auth State
    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin.asStateFlow()

    fun setAdmin(admin: Boolean) {
        _isAdmin.value = admin
    }

    fun getBookById(id: String): Book? {
        return allBooks.find { it.id == id }
    }

    fun reserveBook(book: Book) {
        val newReservation = Loan(
            id = UUID.randomUUID().toString(),
            bookTitle = book.title,
            type = ActivityType.RESERVATION,
            status = ActivityStatus.AWAITING_PICKUP,
            startDate = "02/06/2024", 
            endDate = "05/06/2024"
        )
        _activities.value = listOf(newReservation) + _activities.value
    }

    fun submitPublication(pub: Publication) {
        _publications.value = listOf(pub) + _publications.value
    }

    fun updatePublicationStatus(id: String, newStatus: PublicationStatus) {
        _publications.value = _publications.value.map {
            if (it.id == id) it.copy(status = newStatus) else it
        }
    }
}

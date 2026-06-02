package br.unifor.librify.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val category: String = "Geral",
    val rating: Double = 0.0,
    val imageUrl: String = "",
    val description: String = ""
)

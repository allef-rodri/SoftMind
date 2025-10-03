package br.com.softmind.model

data class AlertResponse(
    val id: String,
    val message: String,
    val category: String,
    val createdAt: String,
    val isRead: Boolean
)

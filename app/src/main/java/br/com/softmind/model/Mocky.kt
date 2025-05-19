package br.com.softmind.model

data class Mocky (
    val status: String = "",
    val message: String = "",
    val code: Int = 0,
    val success: Boolean = false,
    val path: String = ""
)
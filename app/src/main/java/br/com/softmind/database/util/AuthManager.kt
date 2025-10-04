package br.com.softmind.database.util

import java.time.Instant

object AuthManager {
    var token: String? = null
    var expiresAt: String? = null

    fun isTokenValid(): Boolean {
        return expiresAt?.let {
            val expireDate = Instant.parse(it)
            val now = Instant.now()
            now.isBefore(expireDate)
        } ?: false
    }
}
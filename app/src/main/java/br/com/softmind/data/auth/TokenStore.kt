package br.com.softmind.data.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenStore(private val context: Context) {

    companion object {
        private val KEY_JWT = stringPreferencesKey("jwt_token")
    }

    val tokenFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_JWT]
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[KEY_JWT] = token }
    }

    suspend fun clearToken() {
        context.dataStore.edit { it.remove(KEY_JWT) }
    }
}

package br.com.softmind.repository

import android.util.Log
import br.com.softmind.data.remote.RetrofitClient

class CheckinRepository {

    suspend fun salvarHumor(emojiName: String) {
        Log.d("CheckinRepo", "Enviando respostas: $emojiName")
        RetrofitClient.api.salvarEmojiName(emojiName)
    }

}
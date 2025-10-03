package br.com.softmind.repository

import android.util.Log
import br.com.softmind.data.remote.RetrofitClient
import br.com.softmind.model.EmojiName

class CheckinRepository {

    suspend fun salvarHumor(emojiName: EmojiName) {
        Log.d("CheckinRepo", "Enviando respostas: $emojiName")
        RetrofitClient.api.salvarEmojiName(emojiName)
    }

}
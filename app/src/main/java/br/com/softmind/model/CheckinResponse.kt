package br.com.softmind.model

import com.google.gson.annotations.SerializedName

data class EmojiName(
    @SerializedName("emojiName") val emojiName: String
)
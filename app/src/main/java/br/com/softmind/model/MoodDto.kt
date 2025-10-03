package br.com.softmind.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class MoodDto(
    @SerializedName("id") val id: JsonElement,
    @SerializedName("name") val name: String,
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("data") val data: JsonElement
)

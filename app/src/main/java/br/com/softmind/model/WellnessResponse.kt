package br.com.softmind.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class WellnessResponse(
    @SerializedName("id") val id: JsonElement,
    @SerializedName("name") val name: String,
)

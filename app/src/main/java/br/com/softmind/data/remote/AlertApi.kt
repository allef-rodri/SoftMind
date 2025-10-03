package br.com.softmind.data.remote

import br.com.softmind.model.AlertResponse
import br.com.softmind.model.MarkAsReadRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlertApi {

    @GET("api/Alert/GetRecentAlerts")
    suspend fun getRecentAlerts(): List<AlertResponse>

    @POST("api/Alert/MarkAsRead")
    suspend fun markAsRead(@Body request: MarkAsReadRequest)

    @GET("api/Alert/GetRandomAlert")
    suspend fun getRandomAlert(): AlertResponse
}

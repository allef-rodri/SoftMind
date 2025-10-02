package br.com.softmind.data.remote

import android.content.Context
import br.com.softmind.database.util.getAndroidId
import okhttp3.Interceptor
import okhttp3.Response

class DeviceIdInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val deviceId = context.getAndroidId()
        val newRequest = chain.request().newBuilder()
            .addHeader("X-Device-Id", deviceId)
            .build()
        return chain.proceed(newRequest)
    }
}
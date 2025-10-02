package br.com.softmind.database.util

import android.content.Context
import android.provider.Settings

fun Context.getAndroidId(): String {
    return Settings.Secure.getString(
        contentResolver,
        Settings.Secure.ANDROID_ID
    ) ?: ""
}

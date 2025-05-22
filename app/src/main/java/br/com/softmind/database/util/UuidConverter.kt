package br.com.softmind.database.util

import androidx.room.TypeConverter
import com.android.identity.util.UUID

class UuidConverter {
    @TypeConverter
    fun fromUuid(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUuid(uuidString: String?): UUID? {
        return uuidString?.let { UUID.fromString(it) }
    }
}

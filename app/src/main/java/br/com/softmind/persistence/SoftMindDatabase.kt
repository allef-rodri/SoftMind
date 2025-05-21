package br.com.softmind.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.softmind.converter.UUIDConverter
import br.com.softmind.dao.CheckInResponseDao
import br.com.softmind.dao.SessionDao
import br.com.softmind.model.CheckInResponse
import br.com.softmind.model.Session

@Database(
    entities = [Session::class, CheckInResponse::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UUIDConverter::class)
abstract class SoftMindDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun checkInResponseDao(): CheckInResponseDao
}
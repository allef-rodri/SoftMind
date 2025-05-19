package br.com.softmind.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.softmind.persistence.converters.DateTimeConverter
import br.com.softmind.persistence.converters.UUIDConverter
import br.com.softmind.persistence.dao.UserDao
import br.com.softmind.persistence.entities.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateTimeConverter::class, UUIDConverter::class)
abstract class SoftMindDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE : SoftMindDatabase? = null

        fun getInstance(context: Context): SoftMindDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SoftMindDatabase::class.java,
                    "softmind_database"
                )
                .fallbackToDestructiveMigration() // Opcional: use apenas em desenvolvimento
                .build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
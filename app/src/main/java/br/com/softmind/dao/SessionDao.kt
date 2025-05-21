package br.com.softmind.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.softmind.model.Session
import java.util.UUID

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session)

    @Query("SELECT * FROM sessions WHERE date >= :startOfDay AND date <= :endOfDay")
    suspend fun getSessionForDate(startOfDay: Long, endOfDay: Long): Session?

    @Query("DELETE FROM sessions WHERE sessionId = :sessionId")
    suspend fun deleteSession(sessionId: UUID)
}

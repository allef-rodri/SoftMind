package br.com.softmind.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.softmind.model.CheckInResponse
import java.util.UUID

@Dao
interface CheckInResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponse(response: CheckInResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponses(responses: List<CheckInResponse>)

    @Query("SELECT * FROM checkin_responses WHERE sessionId = :sessionId")
    suspend fun getResponsesForSession(sessionId: UUID): List<CheckInResponse>

    @Query(
        """
        SELECT COUNT(*) > 0 
        FROM checkin_responses cr 
        INNER JOIN sessions s ON cr.sessionId = s.sessionId 
        WHERE s.date >= :startOfDay AND s.date <= :endOfDay
    """
    )
    suspend fun hasCheckinForDate(startOfDay: Long, endOfDay: Long): Boolean
}

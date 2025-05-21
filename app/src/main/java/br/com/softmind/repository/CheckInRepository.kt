package br.com.softmind.repository

import br.com.softmind.dao.CheckInResponseDao
import br.com.softmind.dao.SessionDao
import br.com.softmind.model.CheckInResponse
import br.com.softmind.model.Session
import java.util.Calendar
import java.util.UUID

class CheckInRepository (
    private val sessionDao: SessionDao,
    private val checkInResponseDao: CheckInResponseDao
) {
    suspend fun hasCheckInForToday(): Boolean {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        return checkInResponseDao.hasCheckinForDate(startOfDay, endOfDay)
    }

    suspend fun saveCheckInResponses(responses: List<Pair<UUID, String>>) {
        val session = Session()
        val sessionId = session.sessionId
        sessionDao.insertSession(session)


        val checkInResponses = responses.map { (questionId, answer) ->
            CheckInResponse(
                sessionId = sessionId,
                questionId = questionId,
                answer = answer
            )
        }

        checkInResponseDao.insertResponses(checkInResponses)
    }
}
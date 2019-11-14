package com.example.android.ratingbrowser.data.resources

import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.TournamentApiData
import com.example.android.ratingbrowser.data.db.AppDatabase
import com.example.android.ratingbrowser.data.db.TournamentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

class TournamentApiResource(
    queries: Queries,
    database: AppDatabase,
    scope: CoroutineScope = GlobalScope
) : Resource<TournamentApiData, Int>(queries, database, scope) {
    private val tournamentsDao = database.tournamentDao()

    override suspend fun getFromDb(payload: Int): TournamentApiData? {
        val entity = tournamentsDao.getTournament(payload) ?: return null
        return TournamentApiData(
            id = entity.id,
            name = entity.name,
            startDate = entity.startDate,
            endDate = entity.endDate,
            questions = entity.questions
        )
    }

    override suspend fun saveToDb(data: TournamentApiData) {
        val entity = TournamentEntity(
            data.id,
            data.name,
            data.startDate,
            data.endDate,
            data.questions
        )
        tournamentsDao.add(entity)
    }

    override suspend fun getFromNetwork(payload: Int): TournamentApiData {
        val response = queries.getTournamentInfoApi(payload).first()
        return TournamentApiData(
            id = response.idtournament.toInt(),
            name = response.longName,
            startDate = response.dateStart,
            endDate = response.dateEnd,
            questions = response.questionsTotal
        )
    }
}
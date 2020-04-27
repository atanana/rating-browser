package com.example.android.ratingbrowser.data.resources

import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.TournamentApiData
import com.example.android.ratingbrowser.data.db.AppDatabase
import com.example.android.ratingbrowser.data.db.TournamentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TournamentApiResource(
    queries: Queries,
    database: AppDatabase,
    scope: CoroutineScope
) : Resource<TournamentApiData, Int>(queries, database, scope) {
    private val tournamentsDao = database.tournamentDao()

    override fun getFromDb(payload: Int): Flow<TournamentApiData?> =
        tournamentsDao.getTournament(payload).map { entity ->
            entity ?: return@map null
            TournamentApiData(
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
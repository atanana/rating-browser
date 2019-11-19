package com.example.android.ratingbrowser.data.resources

import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.TournamentShort
import com.example.android.ratingbrowser.data.db.AppDatabase
import com.example.android.ratingbrowser.data.db.TournamentShortEntity
import com.example.android.ratingbrowser.data.parsers.TournamentsPageParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TournamentsListResource(
    queries: Queries,
    database: AppDatabase,
    private val tournamentsPageParser: TournamentsPageParser,
    scope: CoroutineScope
) : ListResource<TournamentShort>(queries, database, scope) {
    private val tournamentsShortDao = database.tournamentShortDao()

    override suspend fun getFromDb(): List<TournamentShort> =
        tournamentsShortDao.tournaments().map { it.toData() }

    override suspend fun getFromNetwork(): List<TournamentShort> {
        val response = queries.getTournaments()
        return withContext(Dispatchers.Default) {
            tournamentsPageParser.parse(response)
        }
    }

    override suspend fun saveToDb(data: List<TournamentShort>) {
        tournamentsShortDao.clear()
        val entities = data.map { it.toEntity() }
        tournamentsShortDao.add(entities)
    }

    private fun TournamentShort.toEntity(): TournamentShortEntity = TournamentShortEntity(
        id = id,
        name = name,
        type = type,
        endDate = endDate,
        difficulty = difficulty
    )

    private fun TournamentShortEntity.toData(): TournamentShort = TournamentShort(
        id = id,
        name = name,
        type = type,
        endDate = endDate,
        difficulty = difficulty
    )
}
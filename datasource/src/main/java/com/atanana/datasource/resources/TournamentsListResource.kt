package com.atanana.datasource.resources

import com.atanana.common.TournamentShort
import com.atanana.datasource.Queries
import com.atanana.datasource.database.AppDatabase
import com.atanana.datasource.database.TournamentShortEntity
import com.atanana.datasource.parsers.TournamentsPageParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TournamentsListResource(
    queries: Queries,
    database: AppDatabase,
    private val tournamentsPageParser: TournamentsPageParser,
    scope: CoroutineScope
) : ListResource<TournamentShort>(queries, database, scope) {
    private val tournamentsShortDao = database.tournamentShortDao()

    override fun getFromDb(): Flow<List<TournamentShort>> =
        tournamentsShortDao.tournaments().map { list ->
            list.map { it.toData() }
        }

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

    private fun TournamentShort.toEntity(): TournamentShortEntity =
        TournamentShortEntity(
            id = id,
            name = name,
            type = type,
            endDate = endDate,
            difficulty = difficulty
        )

    private fun TournamentShortEntity.toData(): TournamentShort =
        TournamentShort(
            id = id,
            name = name,
            type = type,
            endDate = endDate,
            difficulty = difficulty
        )
}
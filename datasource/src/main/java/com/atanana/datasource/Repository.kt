package com.atanana.datasource

import com.atanana.common.TournamentApiData
import com.atanana.common.TournamentPageData
import com.atanana.common.TournamentShort
import com.atanana.datasource.resources.TournamentApiResource
import com.atanana.datasource.resources.TournamentPageResource
import com.atanana.datasource.resources.TournamentsListResource
import kotlinx.coroutines.flow.Flow

class Repository(
    private val tournamentApiResource: TournamentApiResource,
    private val tournamentPageResource: TournamentPageResource,
    private val tournamentsListResource: TournamentsListResource
) {
    fun getTournaments(): Flow<List<TournamentShort>> = tournamentsListResource.get()

    fun getTournamentPage(tournamentId: Int): Flow<TournamentPageData> =
        tournamentPageResource.get(tournamentId)

    fun getTournamentFromApi(tournamentId: Int): Flow<TournamentApiData> =
        tournamentApiResource.get(tournamentId)
}
package com.example.android.ratingbrowser.data

import com.example.android.ratingbrowser.data.db.*
import com.example.android.ratingbrowser.data.parsers.TournamentPageParser
import com.example.android.ratingbrowser.data.parsers.TournamentsPageParser
import kotlinx.coroutines.*
import timber.log.Timber

class Repository(
    private val queries: Queries,
    database: AppDatabase,
    private val tournamentsPageParser: TournamentsPageParser,
    private val tournamentPageParser: TournamentPageParser,
    private val repositoryScope: CoroutineScope = GlobalScope
) {
    private val relationsDao = database.personRelationsDao()
    private val tournamentsDao = database.tournamentDao()
    private val personsDao = database.personsDao()

    suspend fun getTournaments(): List<TournamentShort> {
        val response = queries.getTournaments()
        return withContext(Dispatchers.Default) {
            tournamentsPageParser.parse(response)
        }
    }

    suspend fun getTournamentPage(tournamentId: Int): TournamentPageData {
        val entity = loadTournamentPageDataFromDb(tournamentId)
        return if (entity != null) {
            repositoryScope.launch {
                loadTournamentPage(tournamentId)
            }
            entity
        } else {
            loadTournamentPage(tournamentId)
        }
    }

    private suspend fun loadTournamentPage(tournamentId: Int): TournamentPageData {
        val response = queries.getTournamentInfo(tournamentId)
        val data = withContext(Dispatchers.Default) {
            tournamentPageParser.parse(response).toData()
        }
        relationsDao.clearRelations(tournamentId)
        saveRelations(tournamentId, PersonRelationType.EDITOR, data.editors)
        saveRelations(tournamentId, PersonRelationType.GAME_JURY, data.gameJury)
        saveRelations(tournamentId, PersonRelationType.APPEALS_JURY, data.appealJury)
        return data
    }

    private suspend fun saveRelations(
        tournamentId: Int,
        type: PersonRelationType,
        persons: List<String>
    ) {
        for (person in persons) {
            val personId = personsDao.tryAdd(PersonEntity(name = person))
            Timber.e("test $personId")
            relationsDao.add(PersonRelationEntity(type, tournamentId, personId))
        }
    }

    private suspend fun loadTournamentPageDataFromDb(tournamentId: Int): TournamentPageData? {
        val editors = relationsDao.getRelations(tournamentId, PersonRelationType.EDITOR)
        val gameJury = relationsDao.getRelations(tournamentId, PersonRelationType.GAME_JURY)
        val appealsJury = relationsDao.getRelations(tournamentId, PersonRelationType.APPEALS_JURY)
        return if (editors.isEmpty() && gameJury.isEmpty() && appealsJury.isEmpty()) {
            null
        } else {
            TournamentPageData(
                editors = editors.toStrings(),
                gameJury = gameJury.toStrings(),
                appealJury = appealsJury.toStrings()
            )
        }
    }

    suspend fun getTournamentFromApi(tournamentId: Int): TournamentApiData {
        val tournament = tournamentsDao.getTournament(tournamentId)
        return if (tournament != null) {
            repositoryScope.launch {
                loadTournamentFromApi(tournamentId)
            }
            tournament.toData()
        } else {
            loadTournamentFromApi(tournamentId)
        }
    }

    private suspend fun loadTournamentFromApi(tournamentId: Int): TournamentApiData {
        val response = queries.getTournamentInfoApi(tournamentId).first()
        val tournamentEntity = TournamentEntity(
            tournamentId,
            response.longName,
            response.dateStart,
            response.dateEnd,
            response.questionsTotal
        )
        tournamentsDao.add(tournamentEntity)
        return response.toData()
    }
}
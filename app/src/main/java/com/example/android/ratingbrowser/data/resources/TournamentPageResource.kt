package com.example.android.ratingbrowser.data.resources

import com.example.android.ratingbrowser.data.Queries
import com.example.android.ratingbrowser.data.TournamentPageData
import com.example.android.ratingbrowser.data.db.*
import com.example.android.ratingbrowser.data.parsers.TournamentPageParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TournamentPageResource(
    queries: Queries,
    database: AppDatabase,
    private val tournamentPageParser: TournamentPageParser,
    scope: CoroutineScope
) : Resource<TournamentPageData, Int>(queries, database, scope) {
    private val relationsDao = database.personRelationsDao()
    private val personsDao = database.personsDao()

    override suspend fun getFromDb(payload: Int): TournamentPageData? {
        val editors = relationsDao.getRelations(payload, PersonRelationType.EDITOR)
        val gameJury = relationsDao.getRelations(payload, PersonRelationType.GAME_JURY)
        val appealsJury = relationsDao.getRelations(payload, PersonRelationType.APPEALS_JURY)
        return if (editors.isEmpty() && gameJury.isEmpty() && appealsJury.isEmpty()) {
            null
        } else {
            TournamentPageData(
                id = payload,
                editors = editors.toStrings(),
                gameJury = gameJury.toStrings(),
                appealJury = appealsJury.toStrings()
            )
        }
    }

    private fun Collection<PersonEntity>.toStrings() = map { it.name }

    override suspend fun saveToDb(data: TournamentPageData) {
        relationsDao.clearRelations(data.id)
        saveRelations(data.id, PersonRelationType.EDITOR, data.editors)
        saveRelations(data.id, PersonRelationType.GAME_JURY, data.gameJury)
        saveRelations(data.id, PersonRelationType.APPEALS_JURY, data.appealJury)
    }

    private suspend fun saveRelations(
        tournamentId: Int,
        type: PersonRelationType,
        persons: List<String>
    ) {
        for (person in persons) {
            val personId = personsDao.tryAdd(PersonEntity(name = person))
            relationsDao.add(PersonRelationEntity(type, tournamentId, personId))
        }
    }

    override suspend fun getFromNetwork(payload: Int): TournamentPageData {
        val response = queries.getTournamentInfo(payload)
        val (editors, gameJury, appealJury) = withContext(Dispatchers.Default) {
            tournamentPageParser.parse(response)
        }
        return TournamentPageData(
            id = payload,
            editors = editors,
            gameJury = gameJury,
            appealJury = appealJury
        )
    }
}
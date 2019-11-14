package com.example.android.ratingbrowser.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TournamentsShortDao {
    @Query("select * from tournaments_short where id = :id")
    suspend fun getTournament(id: Int): TournamentShortEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(tournament: TournamentShortEntity)
}

@Dao
interface TournamentsDao {
    @Query("select * from tournaments where id = :id")
    suspend fun getTournament(id: Int): TournamentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(tournament: TournamentEntity)
}

@Dao
interface PersonsDao {
    @Query("select * from persons where id = :id")
    suspend fun getPerson(id: Int): PersonEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(person: PersonEntity): Long

    @Query("select id from persons where name = :name")
    suspend fun getId(name: String): Int
}

suspend fun PersonsDao.tryAdd(person: PersonEntity): Int {
    val id = add(person)
    return if (id != -1L) {
        id.toInt()
    } else {
        getId(person.name)
    }
}

@Dao
interface PersonRelationsDao {
    @Query(
        """
        select * from persons 
        inner join person_relations 
        on persons.id = person_relations.personId 
        where person_relations.type = :type and person_relations.tournamentId = :tournamentId
    """
    )
    suspend fun getRelations(tournamentId: Int, type: PersonRelationType): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(personRelation: PersonRelationEntity)

    @Query("delete from person_relations where tournamentId = :tournamentId")
    suspend fun clearRelations(tournamentId: Int)
}
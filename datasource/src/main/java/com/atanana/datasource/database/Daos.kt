package com.atanana.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TournamentsShortDao {
    @Query("select * from tournaments_short order by endDate desc")
    fun tournaments(): Flow<List<TournamentShortEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(tournaments: List<TournamentShortEntity>)

    @Query("delete from tournaments_short")
    suspend fun clear()
}

@Dao
interface TournamentsDao {
    @Query("select * from tournaments where id = :id")
    fun getTournament(id: Int): Flow<TournamentEntity?>

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
    fun getRelations(tournamentId: Int, type: PersonRelationType): Flow<List<PersonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(personRelation: PersonRelationEntity)

    @Query("delete from person_relations where tournamentId = :tournamentId")
    suspend fun clearRelations(tournamentId: Int)
}
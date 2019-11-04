package com.example.android.ratingbrowser.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TournamentDao {
    @Query("select * from tournaments where id = :id")
    suspend fun getTournament(id: Int): TournamentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(tournament: TournamentEntity)
}

@Dao
interface PersonsDao {
    @Query("select * from persons where id = :id")
    suspend fun getPerson(id: Int): PersonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(person: PersonEntity)
}

@Dao
interface PersonRelationsDao {
    @Query("select * from person_relations where tournamentId = :tournamentId and type = :type")
    suspend fun getRelations(tournamentId: Int, type: RelationType): List<PersonRelationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(personRelation: PersonRelationEntity)
}
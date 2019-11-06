package com.example.android.ratingbrowser.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TournamentEntity::class, PersonEntity::class, PersonRelationEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tournamentDao(): TournamentsDao

    abstract fun personsDao(): PersonsDao

    abstract fun personRelationsDao(): PersonRelationsDao
}
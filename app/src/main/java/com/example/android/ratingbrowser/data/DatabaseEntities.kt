package com.example.android.ratingbrowser.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tournaments")
data class TournamentEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val startDate: String,
    val endDate: String,
    val questions: Int
)

@Entity(tableName = "persons", indices = [Index(value = ["name"], unique = true)])
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)

@Entity(
    tableName = "person_relations", foreignKeys = [
        ForeignKey(
            entity = TournamentEntity::class,
            parentColumns = ["id"],
            childColumns = ["tournamentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["id"],
            childColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonRelationEntity(val type: RelationType, val tournamentId: Int, val personId: Int)

enum class RelationType {
    EDITOR, GAME_JURY, APPEALS_JURY
}
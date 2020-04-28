package com.example.android.ratingbrowser.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.android.ratingbrowser.data.TournamentType
import org.threeten.bp.LocalDate

@Entity(tableName = "tournaments_short")
data class TournamentShortEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val endDate: LocalDate,
    val type: TournamentType,
    val difficulty: Float?
)

@Entity(tableName = "tournaments")
data class TournamentEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val startDate: String,
    val endDate: String,
    val questions: String
)

@Entity(tableName = "persons", indices = [Index(value = ["name"], unique = true)])
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)

@Entity(
    tableName = "person_relations", foreignKeys = [
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["id"],
            childColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    primaryKeys = ["tournamentId", "personId", "type"]
)
data class PersonRelationEntity(
    val type: PersonRelationType,
    val tournamentId: Int,
    val personId: Int
)

enum class PersonRelationType {
    EDITOR, GAME_JURY, APPEALS_JURY
}
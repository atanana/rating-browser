package com.example.android.ratingbrowser.data.db

import androidx.room.TypeConverter
import com.example.android.ratingbrowser.data.TournamentType
import org.threeten.bp.LocalDate

class Converters {
    @TypeConverter
    fun fromPersonRelationType(type: PersonRelationType): Int = type.ordinal

    @TypeConverter
    fun toPersonRelationType(type: Int): PersonRelationType = PersonRelationType.values()[type]

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toString()

    @TypeConverter
    fun toLocalDate(data: String): LocalDate = LocalDate.parse(data)

    @TypeConverter
    fun fromTournamentType(type: TournamentType): Int = type.ordinal

    @TypeConverter
    fun toTournamentType(type: Int): TournamentType = TournamentType.values()[type]
}
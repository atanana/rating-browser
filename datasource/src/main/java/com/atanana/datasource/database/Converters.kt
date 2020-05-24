package com.atanana.datasource.database

import androidx.room.TypeConverter
import com.atanana.common.TournamentType
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class Converters {
    @TypeConverter
    fun fromPersonRelationType(type: PersonRelationType): Int = type.ordinal

    @TypeConverter
    fun toPersonRelationType(type: Int): PersonRelationType = PersonRelationType.values()[type]

    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long =
        date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    @TypeConverter
    fun toLocalDate(data: Long): LocalDate =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(data), ZoneId.systemDefault()).toLocalDate()

    @TypeConverter
    fun fromTournamentType(type: TournamentType): Int = type.ordinal

    @TypeConverter
    fun toTournamentType(type: Int): TournamentType = TournamentType.values()[type]
}
package com.example.android.ratingbrowser.data.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromPersonRelationType(type: PersonRelationType): Int = type.ordinal

    @TypeConverter
    fun toPersonRelationType(type: Int): PersonRelationType = PersonRelationType.values()[type]
}
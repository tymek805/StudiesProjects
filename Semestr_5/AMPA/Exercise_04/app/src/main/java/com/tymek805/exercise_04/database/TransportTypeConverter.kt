package com.tymek805.exercise_04.database

import androidx.room.TypeConverter

class TransportTypeConverter {
    @TypeConverter
    fun fromTransportType(transportType: TransportType): String {
        return transportType.value
    }

    @TypeConverter
    fun toTransportType(value: String): TransportType {
        return TransportType.fromValue(value) ?: TransportType.BUS
    }
}
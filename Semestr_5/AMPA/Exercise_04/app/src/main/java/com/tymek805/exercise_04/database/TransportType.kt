package com.tymek805.exercise_04.database

enum class TransportType(val value: String) {
    AIRPLANE("Airplane"),
    TRAIN("Train"),
    BUS("Bus");

    companion object {
        fun fromValue(value: String): TransportType? = entries.find { it.value == value }
    }
}
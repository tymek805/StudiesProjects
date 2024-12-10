package com.tymek805.exercise_04.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "items")
data class MyItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var destination: String?,
    var subText: String?,
    @ColumnInfo(name = "rating")
    var rating: Float,
    @ColumnInfo(name = "time")
    var time: Int,
    @ColumnInfo(name = "type")
    var transportType: TransportType?,
    @ColumnInfo(name = "abroad")
    var checked: Boolean
) {
    constructor() : this(0,"","",0f,0,TransportType.BUS,false)
    constructor(num: Int) : this(0,"","", 0f,0,null,false) {
        val cities = listOf("Warszawa", "Kraków", "Poznań", "Wrocław", "Gdańsk", "Szczecin", "Łódź", "Katowice", "Lublin", "Berlin")
        destination = cities.random()
        time = Random.nextInt(60, 250)
        subText = "Czas połączenia: $time min"
        rating = Random.nextInt(0, 5).toFloat()
        transportType = TransportType.entries.toTypedArray().random()
        checked = destination == "Berlin"
    }
}
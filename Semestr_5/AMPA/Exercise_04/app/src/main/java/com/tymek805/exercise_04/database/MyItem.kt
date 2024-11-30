package com.tymek805.exercise_04.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "items")
data class MyItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var textMain: String?,
    var subText: String?,
    @ColumnInfo(name = "rating")
    var rating: Int,
    @ColumnInfo(name = "age")
    var age: Int,
    @ColumnInfo(name = "type")
    var itemType: Boolean,
    @ColumnInfo(name = "selection")
    var checked: Boolean
) {
    constructor() : this(0,"","",0,0,false,false)
    constructor(num: Int) : this(0,"","",0,0,false,false) {
        textMain = "Item name $num"
        subText = "Default text $num"
        rating = Random.nextInt(0, 5)
        age = Random.nextInt(0, 100)
        itemType = Random.nextBoolean()
        checked = false
    }
}
package com.tymek805.exercise_04.database

import kotlin.random.Random

class MyItem(i: Int) {

    var text_main : String = "Default text"
    var text_2 : String = "Default text"
    var item_value : Int = Random.nextInt(0, 5)
    var item_value2: Int = 0
    var item_type : Boolean = Random.nextBoolean()
    var item_checked : Boolean = Random.nextBoolean()
}
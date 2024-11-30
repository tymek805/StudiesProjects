package com.tymek805.exercise_04.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {
    @Query("SELECT * FROM items ORDER BY id ASC")
    fun getAllData(): MutableList<MyItem>?

    @Query("DELETE FROM items")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: MyItem): Long

    @Delete
    fun delete(item: MyItem): Int
}
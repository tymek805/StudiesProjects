package com.tymek805.exercise_06.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MyItem::class], version = 1)
@TypeConverters(TransportTypeConverter::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun myDao(): MyDao?
    companion object {
        @Volatile
        private var databaseInstance: MyDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MyDatabase? {
            if (databaseInstance == null) {
                databaseInstance = databaseBuilder(context.applicationContext, MyDatabase::class.java, "item_database").allowMainThreadQueries().build()
            }
            return databaseInstance
        }
    }
}
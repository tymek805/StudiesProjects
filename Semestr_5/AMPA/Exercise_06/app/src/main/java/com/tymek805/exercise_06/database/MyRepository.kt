package com.tymek805.exercise_06.database

import android.content.Context
import androidx.lifecycle.LiveData

class MyRepository(ctx: Context) {
    private var database: MyDatabase
    private var myDao: MyDao

    fun getAllItems(): LiveData<List<MyItem>> {
        return myDao.getAllData()
    }

    fun deleteItem(myItem: MyItem) {
        myDao.delete(myItem)
    }

    fun addItem(myItem: MyItem) {
        myDao.insert(myItem)
    }

    fun updateItem(myItem: MyItem) {
        myDao.updateItem(myItem)
    }

    companion object {
        private var instance: MyRepository? = null
        fun getInstance(ctx: Context): MyRepository {
            if (instance == null) {
                instance = MyRepository(ctx)
            }
            return instance as MyRepository
        }
    }

    init {
        database = MyDatabase.getDatabase(ctx)!!
        myDao = database.myDao()!!

        myDao.deleteAll()
        for (i in 1..10) {
            myDao.insert(MyItem(i))
        }
    }
}
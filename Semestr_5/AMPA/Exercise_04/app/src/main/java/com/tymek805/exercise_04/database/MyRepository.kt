package com.tymek805.exercise_04.database

import android.content.Context

class MyRepository(ctx: Context) {
    private val listSize = 15
    private var dataList: MutableList<MyItem> = mutableListOf()

    fun getAllItems(): MutableList<MyItem> {
        return dataList
    }

    fun deleteItem(myItem: MyItem) {
        dataList.remove(myItem)
    }

//    fun addItem()
//    fun deleteItem()
//    fun updateItem()

    companion object {
        private var INSTANCE: MyRepository? = null
        fun getInstance(ctx: Context): MyRepository {
            if (INSTANCE == null) {
                INSTANCE = MyRepository(ctx)
            }
            return INSTANCE as MyRepository
        }
    }

    init {
        dataList = MutableList(listSize) { i -> MyItem(i) }
    }
}
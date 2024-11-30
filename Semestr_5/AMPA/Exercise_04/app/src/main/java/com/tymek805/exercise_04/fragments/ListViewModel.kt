package com.tymek805.exercise_04.fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.tymek805.exercise_04.database.MyItem
import com.tymek805.exercise_04.database.MyRepository

class ListViewModel(context: Context) : ViewModel() {
    private val repository: MyRepository = MyRepository.getInstance(context)

    fun getAllItems() : MutableList<MyItem>? = repository.getAllItems()
    fun deleteItem(item: MyItem) {
        repository.deleteItem(item)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>,
                                                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ListViewModel(application.applicationContext) as T
            }
        }
    }
}
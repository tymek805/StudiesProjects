package com.tymek805.exercise_04.fragments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.tymek805.exercise_04.database.MyItem
import com.tymek805.exercise_04.database.MyRepository

class ListViewModel(context: Context) : ViewModel() {
    private val repository: MyRepository = MyRepository.getInstance(context)

    private val _selectedItem = MutableLiveData<MyItem?>()
    val selectedItem: LiveData<MyItem?> get() = _selectedItem

    fun getAllItems() : MutableList<MyItem>? = repository.getAllItems()

    fun addItem(item: MyItem) {
        repository.addItem(item)
    }

    fun deleteItem(item: MyItem) {
        repository.deleteItem(item)
    }

    fun updateItem(item: MyItem) {
        repository.updateItem(item)
    }

    fun selectItem(item: MyItem) {
        _selectedItem.value = item
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object :
            ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>,
                                                extras: CreationExtras
            ): T {
                return ListViewModel(checkNotNull(extras[APPLICATION_KEY]).applicationContext) as T
            }
        }
    }
}
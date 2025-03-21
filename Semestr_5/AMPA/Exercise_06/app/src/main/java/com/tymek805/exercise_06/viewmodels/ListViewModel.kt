package com.tymek805.exercise_06.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tymek805.exercise_06.database.MyItem
import com.tymek805.exercise_06.database.MyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(application: Application): AndroidViewModel(application) {
    private val appContext = application.applicationContext
    private val repository: MyRepository = MyRepository.getInstance(appContext)

    private val _selectedItem = MutableLiveData<MyItem?>()
    val selectedItem: LiveData<MyItem?> get() = _selectedItem

    val items: LiveData<List<MyItem>> = repository.getAllItems()

    fun getAllItems() : LiveData<List<MyItem>> = repository.getAllItems()

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

    fun toggleChecked(item: MyItem) {
        val updatedItem = item.copy(checked = !item.checked)
        viewModelScope.launch {
            repository.updateItem(updatedItem)
        }
    }
}
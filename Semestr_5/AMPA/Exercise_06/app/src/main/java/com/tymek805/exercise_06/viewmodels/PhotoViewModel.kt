package com.tymek805.exercise_06.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class PhotoViewModel(application: Application) : AndroidViewModel(application) {
    private val appContext = application.applicationContext

    private val _photoGallery = MutableStateFlow<List<Uri>>(emptyList())
    val photoGallery: StateFlow<List<Uri>> = _photoGallery

    init {
        loadSavedPhotos()
    }

    fun addPhoto(uri: Uri) {
        _photoGallery.value += uri
        savePhotos()
    }

    private fun savePhotos() {
        viewModelScope.launch {
            val file = File(appContext.filesDir, "photos.txt")
            file.writeText(_photoGallery.value.joinToString("\n") { it.toString() })
        }
    }

    private fun loadSavedPhotos() {
        viewModelScope.launch {
            val file = File(appContext.filesDir, "photos.txt")
            if (file.exists()) {
                val uris = file.readLines().map { Uri.parse(it) }
                _photoGallery.value = uris
            }
        }
    }
}

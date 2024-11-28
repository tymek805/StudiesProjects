package com.tymek805.exercise_03

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RightViewModel : ViewModel() {
    private val _rating = MutableLiveData<Float>()
    val rating: LiveData<Float> get() = _rating

    fun setRating(newRating: Float) {
        _rating.value = newRating
    }
}
package com.tymek805.exercise_03

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppSettingsViewModel : ViewModel() {
    private val _invitation: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = "Invitation"
    }

    private val _author: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = "Tymoteusz Lango"
    }

    private val _switch: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    val invitation: LiveData<String> get() = _invitation
    val author: LiveData<String> get() = _author
    val switch: LiveData<Boolean> get() = _switch

    fun setInvitation(newInvitation: String) {
        _invitation.value = newInvitation
    }

    fun setAuthor(newAuthor: String) {
        _author.value = newAuthor
    }

    fun setSwitch(newSwitch: Boolean) {
        _switch.value = newSwitch
    }

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> get() = _msg

    fun setMsg(newMessage: String) {
        _msg.value = newMessage
    }
}
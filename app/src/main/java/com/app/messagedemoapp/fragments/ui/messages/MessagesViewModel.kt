package com.app.messagedemoapp.fragments.ui.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.messagedemoapp.models.MessagesModel
import com.app.messagedemoapp.repository.MessagesRepository

class MessagesViewModel : ViewModel() {
    fun getAllMessages(): LiveData<List<MessagesModel>>? {
        return MessagesRepository.getMessages()
    }
}
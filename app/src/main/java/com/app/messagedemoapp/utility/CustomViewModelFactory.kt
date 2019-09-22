package com.app.messagedemoapp.utility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.messagedemoapp.activity.ui.message.MessageViewModel
import com.app.messagedemoapp.fragments.ui.contacts.ContactsViewModel
import com.app.messagedemoapp.fragments.ui.messages.MessagesViewModel

class CustomViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ContactsViewModel::class.java) -> ContactsViewModel() as T
            modelClass.isAssignableFrom(MessagesViewModel::class.java) -> MessagesViewModel() as T
            modelClass.isAssignableFrom(MessageViewModel::class.java) -> MessageViewModel() as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
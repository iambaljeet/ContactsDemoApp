package com.app.messagedemoapp.repository

import androidx.lifecycle.LiveData
import com.app.MyApp
import com.app.messagedemoapp.database.MessagesDao
import com.app.messagedemoapp.database.MessagesDatabase
import com.app.messagedemoapp.models.MessagesModel

object MessagesRepository {
    var messagesDatabase: MessagesDatabase? = null
    var messagesDao: MessagesDao? = null

    init {
        messagesDatabase = MyApp.APPLICATION_INSTANCE.let { MessagesDatabase.getDatabase(it) }

        if (messagesDatabase != null) {
            messagesDao = messagesDatabase?.messagesDao()
        }
    }

    fun getMessages(): LiveData<List<MessagesModel>>? {
        return messagesDao?.getAllMessages()
    }
}
package com.app.contactsdemoapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.app.contactsdemoapp.models.MessagesModel

@Database(entities = [MessagesModel::class], version = 1)
abstract class MessagesDatabase: RoomDatabase() {

    companion object {
        @Volatile
        internal var messagesDatabase: MessagesDatabase? = null

//        Returns the instance of database
        fun getDatabase(context: Context): MessagesDatabase? {
            if (messagesDatabase == null) {
                synchronized(MessagesDatabase::class.java) {
                    if (messagesDatabase == null) {
                        messagesDatabase = Room.databaseBuilder(
                            context,
                            MessagesDatabase::class.java, "messages_db"
                        ).build()
                    }
                }
            }
            return messagesDatabase
        }
    }

    abstract fun messagesDao(): MessagesDao
}
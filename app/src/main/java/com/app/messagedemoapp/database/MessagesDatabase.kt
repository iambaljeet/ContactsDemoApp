package com.app.messagedemoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.messagedemoapp.models.MessagesModel
import com.app.messagedemoapp.utility.Constants

@Database(entities = [MessagesModel::class], version = 2, exportSchema = false)
abstract class MessagesDatabase : RoomDatabase() {

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
                            MessagesDatabase::class.java, Constants.DATABASENAME
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return messagesDatabase
        }
    }

    abstract fun messagesDao(): MessagesDao
}
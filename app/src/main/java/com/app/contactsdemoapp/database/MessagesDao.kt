package com.app.contactsdemoapp.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.app.contactsdemoapp.models.MessagesModel

@Dao
interface MessagesDao {
    @Query("Select * from user_messages")
    fun getAllMessages(): LiveData<List<MessagesModel>>

    @Insert(onConflict = REPLACE)
    fun saveMessage(messagesModel: MessagesModel): Long

    @Update(onConflict = REPLACE)
    fun updateMessage(messagesModel: MessagesModel): Int

    @Delete
    fun deleteMessage(messagesModel: MessagesModel)
}
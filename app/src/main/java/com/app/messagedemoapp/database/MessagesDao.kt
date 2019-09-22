package com.app.messagedemoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.app.messagedemoapp.models.MessagesModel

@Dao
interface MessagesDao {
    @Query("Select * from user_messages ORDER BY date_millis DESC")
    fun getAllMessages(): LiveData<List<MessagesModel>>

    @Insert(onConflict = REPLACE)
    fun saveMessage(messagesModel: MessagesModel): Long

    @Update(onConflict = REPLACE)
    fun updateMessage(messagesModel: MessagesModel): Int

    @Delete
    fun deleteMessage(messagesModel: MessagesModel)
}
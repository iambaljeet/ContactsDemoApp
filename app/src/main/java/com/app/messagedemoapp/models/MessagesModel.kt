package com.app.messagedemoapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_messages")
data class MessagesModel(
    @ColumnInfo(name = "user_id") var user_id: Int,
    @PrimaryKey(autoGenerate = true) var message_id: Int,
    @ColumnInfo(name = "user_name") var user_name: String,
    @ColumnInfo(name = "user_number") var user_number: String,
    @ColumnInfo(name = "date_millis") var date_millis: Long,
    @ColumnInfo(name = "message_text") var message_text: String
) {
    constructor() : this(0, 0, "", "", 0L, "")
}
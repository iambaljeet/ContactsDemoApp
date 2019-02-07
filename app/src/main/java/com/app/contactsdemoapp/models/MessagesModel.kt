package com.app.contactsdemoapp.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user_messages")
data class MessagesModel(@ColumnInfo(name = "user_id") var user_id: Int,
                       @PrimaryKey(autoGenerate = true) var message_id: Int,
                       @ColumnInfo(name = "user_name") var user_name: String,
                       @ColumnInfo(name = "user_number") var user_number: String,
                       @ColumnInfo(name = "date") var date: String,
                       @ColumnInfo(name = "message_text") var message_text: String ){
    constructor():this(0, 0, "", "", "", "")
}
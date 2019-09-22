package com.app.messagedemoapp.models

data class SmsApiResponseModelDTO(
    val balance: Int? = null,
    val batchId: Int? = null,
    val cost: Int? = null,
    val numMessages: Int? = null,
    val message: MessageDTO? = null,
    val receiptUrl: String? = null,
    val custom: String? = null,
    val messages: List<MessagesDTO?>? = null,
    val errors: List<ErrorsDTO?>? = null,
    val warnings: List<WarningsDTO?>? = null,
    val status: String? = null
)
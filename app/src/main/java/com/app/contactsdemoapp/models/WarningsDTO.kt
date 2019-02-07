package com.app.contactsdemoapp.models

import com.google.gson.annotations.SerializedName

data class WarningsDTO(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("numbers")
	val numbers: String? = null
)
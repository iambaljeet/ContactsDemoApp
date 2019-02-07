package com.app.contactsdemoapp.models

import com.google.gson.annotations.SerializedName

data class ErrorsDTO(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
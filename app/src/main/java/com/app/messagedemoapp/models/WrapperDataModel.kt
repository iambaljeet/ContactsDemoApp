package com.app.messagedemoapp.models

data class WrapperDataModel<T>(var loading: Boolean, var data: T?, var message: String?)
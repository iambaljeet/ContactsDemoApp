package com.app.messagedemoapp.repository

import androidx.lifecycle.MutableLiveData
import com.app.MyApp
import com.app.messagedemoapp.models.ContactsModelDTO
import com.app.messagedemoapp.utility.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object ContactsRepository {
    private val allContactsLiveData: MutableLiveData<List<ContactsModelDTO>> = MutableLiveData()

    fun getContacts(): MutableLiveData<List<ContactsModelDTO>> {

        CoroutineScope(Dispatchers.IO).launch {
            val fileName = Constants.CONTACTSJSON
            val jsonString =
                MyApp.APPLICATION_INSTANCE.assets?.open(fileName)?.bufferedReader().use {
                    it?.readText()
                }
            val collectionType = object : TypeToken<List<ContactsModelDTO>>() {}.type

            withContext(Dispatchers.Main) {
                allContactsLiveData.value = Gson()
                    .fromJson(jsonString, collectionType) as List<ContactsModelDTO>
            }
        }

        return allContactsLiveData
    }
}
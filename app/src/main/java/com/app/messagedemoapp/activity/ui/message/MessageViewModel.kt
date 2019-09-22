package com.app.messagedemoapp.activity.ui.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.MyApp
import com.app.messagedemoapp.database.MessagesDao
import com.app.messagedemoapp.database.MessagesDatabase
import com.app.messagedemoapp.network.ApiService
import com.app.messagedemoapp.models.ContactsModelDTO
import com.app.messagedemoapp.models.MessagesModel
import com.app.messagedemoapp.models.SmsApiResponseModelDTO
import com.app.messagedemoapp.models.WrapperDataModel
import com.app.messagedemoapp.utility.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessageViewModel : ViewModel() {
    var wrapperDataModelLiveData: MutableLiveData<WrapperDataModel<SmsApiResponseModelDTO>> =
        MutableLiveData()
    var saveDataToDbLiveData: MutableLiveData<Long> = MutableLiveData()
    var messagesDatabase: MessagesDatabase? = null
    var messagesDao: MessagesDao? = null
    var apiService: ApiService? = null
    var wrapperDataModel: WrapperDataModel<SmsApiResponseModelDTO>? = null

    init {
        messagesDatabase = MessagesDatabase.getDatabase(MyApp.APPLICATION_INSTANCE)
        messagesDao = messagesDatabase?.messagesDao()
        apiService = ApiService.create()
    }

    //    Call to API for sending OTP
    fun sendMessage(
        to: String,
        body: String
    ): MutableLiveData<WrapperDataModel<SmsApiResponseModelDTO>> {
        wrapperDataModel = WrapperDataModel(true, null, null)
        wrapperDataModelLiveData.postValue(wrapperDataModel)

        CoroutineScope(Dispatchers.IO).launch {
            val smsApiResponseModelDTOResponse =
                apiService?.sendSms(Constants.APIKEY, body, Constants.SENDERNAME, to)

            withContext(Dispatchers.Main) {
                wrapperDataModel = WrapperDataModel(
                    false,
                    smsApiResponseModelDTOResponse?.body(),
                    smsApiResponseModelDTOResponse?.message()
                )
                wrapperDataModelLiveData.value = wrapperDataModel
            }
        }

        return wrapperDataModelLiveData
    }

    //    Save data to local database
    fun saveData(item: ContactsModelDTO?, item_message: String): MutableLiveData<Long> {
        val messagesModel = MessagesModel()
        messagesModel.user_id = item?.id!!
        messagesModel.user_name = "${item.firstName} ${item.lastName}"
        messagesModel.user_number = item.phoneNumber.toString()
        messagesModel.message_text = item_message
        messagesModel.date_millis = System.currentTimeMillis()

        CoroutineScope(Dispatchers.IO).launch {
            if (messagesDao != null) {

                val result = messagesDao?.saveMessage(messagesModel)

                withContext(Dispatchers.Main) {
                    saveDataToDbLiveData.value = result
                }
            }
        }
        return saveDataToDbLiveData
    }
}
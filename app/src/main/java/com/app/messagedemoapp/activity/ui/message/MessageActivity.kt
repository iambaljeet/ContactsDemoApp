package com.app.messagedemoapp.activity.ui.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.messagedemoapp.R
import com.app.messagedemoapp.models.ContactsModelDTO
import com.app.messagedemoapp.models.SmsApiResponseModelDTO
import com.app.messagedemoapp.models.WrapperDataModel
import com.app.messagedemoapp.utility.Constants
import com.app.messagedemoapp.utility.CustomViewModelFactory
import com.app.messagedemoapp.utility.Utility
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.top_back_toolbar_layout.*
import kotlin.random.Random

class MessageActivity : AppCompatActivity(), View.OnClickListener {

    var otp: Int? = null
    var message: String? = null
    var item: ContactsModelDTO? = null
    private lateinit var messageViewModel: MessageViewModel
    var mutableLiveDataSendMessage: MutableLiveData<WrapperDataModel<SmsApiResponseModelDTO>> =
        MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        messageViewModel =
            ViewModelProviders.of(this, CustomViewModelFactory()).get(MessageViewModel::class.java)

        back_button.setOnClickListener(this)
        send_button.setOnClickListener(this)
        initFields()
    }

    private fun initFields() {
        item = intent.getParcelableExtra(Constants.USERDATA)
        otp = Constants.RANDOM_BASE + Random.nextInt(Constants.RANDOM_END)
        message = getString(R.string.otp_message_prefix) + otp

        item_user_name.text = "${item?.firstName} ${item?.lastName}"
        item_user_number.text = item?.phoneNumber
        item_date.text = Utility.dateFormatter(System.currentTimeMillis())
        item_message.text = message
    }

    //    Click listener for all view
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_button -> finish()
            R.id.send_button -> if (Utility.checkNetworkAvailable(this)) {
                sendOtp()
            } else {
                Toast.makeText(
                    this@MessageActivity,
                    getString(R.string.no_internet),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Call method in viewmodel to send OTP by calling API.
    private fun sendOtp() {
        val to = item?.phoneNumber.toString()
        val body = item_message.text.toString()
        mutableLiveDataSendMessage = messageViewModel.sendMessage(to, body)
        mutableLiveDataSendMessage.observe(this, Observer { wrapperDataModelLiveData ->
            if (wrapperDataModelLiveData?.loading != null && wrapperDataModelLiveData.loading) {
                loading()
            } else {
                if (wrapperDataModelLiveData.data != null && !wrapperDataModelLiveData.data?.errors.isNullOrEmpty()) {
                    if (wrapperDataModelLiveData.data?.errors?.size != null && wrapperDataModelLiveData.data?.errors?.get(
                            0
                        ) != null
                    ) {
                        val errors = wrapperDataModelLiveData.data?.errors
                        Toast.makeText(
                            this@MessageActivity,
                            errors?.get(0)?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        saveToLocalDb()
                    }
                } else {
                    saveToLocalDb()
                }
                loadingFinished()
            }
        })
    }

    // Call method in ViewModel to save message to Local Database
    private fun saveToLocalDb() {
        val saveDataLiveDataModel =
            messageViewModel.saveData(item, item_message.text.toString())

        saveDataLiveDataModel.observe(this, Observer { result ->
            if (result?.compareTo(0) != 0) {
                Toast.makeText(
                    this@MessageActivity,
                    getString(R.string.message_sent),
                    Toast.LENGTH_SHORT
                ).show()
            }
            finish()
        })
    }

    //    Show progress bar
    private fun loading() {
        progressbar.visibility = View.VISIBLE
        send_button.visibility = View.GONE
    }

    //    Hide progress bar
    private fun loadingFinished() {
        progressbar.visibility = View.GONE
        send_button.visibility = View.VISIBLE
    }

    //    Function to launch activity
    companion object {
        fun launchActivity(context: Context?, item: ContactsModelDTO) {
            val intent = Intent(context, MessageActivity::class.java)
            intent.putExtra(Constants.USERDATA, item)
            context?.startActivity(intent)
        }
    }
}
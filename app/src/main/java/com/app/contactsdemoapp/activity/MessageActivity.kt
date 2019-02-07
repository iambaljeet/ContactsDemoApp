package com.app.contactsdemoapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.app.contactsdemoapp.R
import com.app.contactsdemoapp.database.MessagesDao
import com.app.contactsdemoapp.database.MessagesDatabase
import com.app.contactsdemoapp.metwork.ApiService
import com.app.contactsdemoapp.models.ContactsModelDTO
import com.app.contactsdemoapp.models.MessagesModel
import com.app.contactsdemoapp.utility.Constants
import com.app.contactsdemoapp.utility.Utility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.top_back_toolbar_layout.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MessageActivity : AppCompatActivity(), View.OnClickListener {

    var messagesDatabase: MessagesDatabase? = null
    var messagesDao: MessagesDao? = null

    var otp: Int? = null
    var message: String? = null
    var item: ContactsModelDTO? = null

    var disposable: Disposable? = null

    val apiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        back_button.setOnClickListener(this)
        send_button.setOnClickListener(this)
        initfields()
        initDb()
    }

    private fun initfields() {
        item = intent.getParcelableExtra(Constants.USERDATA)
        otp = Constants.RANDOM_BASE + Random.nextInt(Constants.RANDOM_END)
        message = getString(R.string.otp_message_prefix) + otp

        item_user_name.text = """${item?.firstName} ${item?.lastName}"""
        item_user_number.text = item?.phoneNumber
        item_date.text = Utility.dateFromatter(System.currentTimeMillis())
        item_message.text = message
    }

    private fun initDb() {
        messagesDatabase = MessagesDatabase.getDatabase(this)

        if (messagesDatabase != null) {
            messagesDao = messagesDatabase?.messagesDao()
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

//    Click listener for all view
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.back_button -> finish()
            R.id.send_button -> if (Utility.checkNetworkAvailable(this))
                sendMessage()
            else
                Toast.makeText(this@MessageActivity, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
    }

//    Call to API for sending OTP
    fun sendMessage() {
        val to = item?.phoneNumber.toString()
        val body = item_message.text.toString()

        loading()

        disposable =
            apiService?.sendSms(Constants.APIKEY, body, Constants.SENDERNAME, to)?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->
                    loadingFinished()
                    if (result != null && result.status == "success") {
                        saveData()
                    } else {
                        Toast.makeText(this@MessageActivity, result.errors?.get(0)?.message, Toast.LENGTH_SHORT).show()
                    }
                }, { error ->
                    loadingFinished()
                    Toast.makeText(this@MessageActivity, getString(R.string.message_failed), Toast.LENGTH_SHORT).show() })
    }

//    Save data to local database
    fun saveData() {
        val messagesModel: MessagesModel = MessagesModel()
        messagesModel.user_id = item?.id!!
        messagesModel.user_name = """${item?.firstName} ${item?.lastName}"""
        messagesModel.user_number = item?.phoneNumber.toString()
        messagesModel.message_text = item_message.text.toString()
        messagesModel.date = Utility.dateFromatter(System.currentTimeMillis())

        GlobalScope.launch {
            if (messagesDao != null) {

                val result = messagesDao?.saveMessage(messagesModel)

                if (result?.compareTo(0) != 0) {
                    runOnUiThread {
                        finish()
                        Toast.makeText(this@MessageActivity, getString(R.string.message_sent), Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }

//    Show progress bar
    fun loading() {
        progressbar.visibility = View.VISIBLE
        send_button.visibility = View.GONE
    }

//    Hide progress bar
    fun loadingFinished() {
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
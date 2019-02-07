package com.app.contactsdemoapp.utility

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat

object Utility {
//    Format date in milliseconds to Formatted date
    fun dateFromatter(date: Long) : String {
        val format = SimpleDateFormat(Constants.DATE_FORMAT)
        return format.format(date)
    }

//    Check if internet connection in available or not
    fun checkNetworkAvailable(context: Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }
}
package com.app.messagedemoapp.network

import com.app.messagedemoapp.models.SmsApiResponseModelDTO
import com.app.messagedemoapp.utility.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //    Calling send message Api
    @GET("send")
    suspend fun sendSms(
        @Query("apiKey") apiKey: String,
        @Query("message") message: String,
        @Query("sender") sender: String,
        @Query("numbers") numbers: String
    ): Response<SmsApiResponseModelDTO>

    companion object {
        @Volatile
        var apiService: ApiService? = null

        //        Initialize & return Api service
        fun create(): ApiService? {
            synchronized(ApiService::class.java) {
                if (apiService == null) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    val interceptor =
                        httpLoggingInterceptor.apply {
                            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                        }
                    val httpClient = OkHttpClient.Builder().addInterceptor(interceptor)
                    val retrofit = Retrofit.Builder()
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Constants.BASE_URL)
                        .build()

                    apiService = retrofit.create(ApiService::class.java)
                }
                return apiService
            }
        }
    }
}
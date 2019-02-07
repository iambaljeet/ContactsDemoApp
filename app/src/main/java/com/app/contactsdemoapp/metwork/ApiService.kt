package com.app.contactsdemoapp.metwork

import com.app.contactsdemoapp.models.SmsApiResponseModelDTO
import com.app.contactsdemoapp.utility.Constants
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

//    Calling send message Api
    @GET("send")
    fun sendSms(@Query("apikey") apikey: String,
        @Query("message") message: String,
        @Query("sender") sender: String,
        @Query("numbers") numbers: String
    ): Observable<SmsApiResponseModelDTO>

    companion object {
        @Volatile
        var apiService: ApiService? = null

//        Initialize & return Api service
        fun create(): ApiService? {
            synchronized(ApiService::class.java) {
                if (apiService == null) {
                    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    val httpClient = OkHttpClient.Builder().addInterceptor(interceptor)
                    val retrofit = Retrofit.Builder()
                        .client(httpClient.build())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
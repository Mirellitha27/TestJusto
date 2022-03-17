package com.iwebsapp.testjusto.api

import com.google.gson.Gson
import com.iwebsapp.testjusto.BuildConfig
import com.iwebsapp.testjusto.model.error.ErrorResponse
import com.iwebsapp.testjusto.model.error.GeneralError
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiRepository {

    private lateinit var retrofit: Retrofit

    init {
        createRetrofit()
    }

    private fun createRetrofit() {
        val gson = Gson()
        val okHttpClient = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        okHttpClient
            .addInterceptor(
                httpLoggingInterceptor.apply {
                    if (BuildConfig.DEBUG) httpLoggingInterceptor.level =
                        HttpLoggingInterceptor.Level.BODY
                }
            )
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }

    fun getError(response: ResponseBody?): ErrorResponse? {
        val converter = retrofit.responseBodyConverter<ErrorResponse>(
            ErrorResponse::class.java,
            arrayOfNulls(0)
        )

        try {
            if (response != null) {
                return converter.convert(response)
            }
            return getErrorDefault()
        } catch (e: Exception) {
            return getErrorDefault()
        }
    }

    fun getErrorResponse(throwable: Throwable): ErrorResponse {
        return if (throwable is HttpException) {
            return try {
                val body = throwable.response()?.errorBody()
                Gson().fromJson<ErrorResponse>(body?.charStream(), ErrorResponse::class.java)
                    ?: getErrorDefault()
            } catch (e: Exception) {
                getErrorDefault()
            }
        } else {
            getErrorDefault()
        }
    }

    private fun getErrorDefault(): ErrorResponse {
        return ErrorResponse(GeneralError(message = "El servicio no está disponible, por favor intente más tarde"))
    }
}
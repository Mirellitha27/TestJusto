package com.iwebsapp.testjusto.api

import com.iwebsapp.testjusto.model.HomeModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("api/")
    fun getRandomUser(): Observable<HomeModel>
}
package com.dicoding.bangkitapi.data.retrofit

import com.dicoding.bangkitapi.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(@Query("active") active: Int): Call<EventResponse>

    @GET("events")
    fun getEventsActive(@Query("active") active: Int): Call<EventResponse>

//    @GET("events")
//    fun getEventsActive(@Query("finished") active: Int): Call<EventActiveResponse>


}
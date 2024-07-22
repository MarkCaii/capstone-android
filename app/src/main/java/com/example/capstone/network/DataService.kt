package com.example.capstone.network

import retrofit2.Call
import com.example.capstone.model.DataItem
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {
    @GET("getHistory")
    fun fetchData(@Query("user_id") userId: Int): Call<List<DataItem>>
}
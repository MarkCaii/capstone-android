package com.example.capstone.repository

import com.example.capstone.model.DataItem
import com.example.capstone.network.DataService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.0.253:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(DataService::class.java)

    fun fetchData(): Call<List<DataItem>> {
        return service.fetchData(1)
    }
}
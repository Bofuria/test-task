package com.example.task.retrofit

import com.example.task.domain.entities.Item
import com.example.task.domain.entities.ServerDataItem
import com.example.task.domain.entities.ServerData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("entities/getAllIds")
    fun getAllData(): Call<ServerData>

    @GET("object/{id}")
    fun getDataById(@Path("id") id: String): Call<Item>


}
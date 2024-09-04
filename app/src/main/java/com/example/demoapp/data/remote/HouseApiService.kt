package com.example.demoapp.data.remote


import androidx.lifecycle.LiveData
import com.example.demoapp.domain.model.House
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.util.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HouseApiService {

    @GET("search")
    suspend fun getHouse(
        @Query("location") location: String
    ): Response<House>
}
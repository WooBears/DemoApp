package com.example.demoapp.data.remote

import com.example.demoapp.domain.model.carModel.Car
import com.example.demoapp.domain.model.carModel.CarItem
import com.example.demoapp.util.Result
import okhttp3.Response
import retrofit2.http.GET

interface CarApiService {

    @GET("v2/cars/makes")
    suspend fun getCars() : retrofit2.Response<Car>
}
package com.example.demoapp.domain.repository

import com.example.demoapp.domain.model.House
import com.example.demoapp.domain.model.carModel.CarItem
import com.example.demoapp.util.Result

interface CarRepository {
    suspend fun getCar() : Result<List<CarItem>>
}
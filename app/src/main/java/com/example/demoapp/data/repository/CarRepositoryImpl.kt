package com.example.demoapp.data.repository

import com.example.demoapp.data.baseRepository.BaseDataSource
import com.example.demoapp.data.remote.CarApiService
import com.example.demoapp.domain.model.carModel.CarItem
import com.example.demoapp.domain.repository.CarRepository
import com.example.demoapp.util.Result


class CarRepositoryImpl(
    private val carApiService: CarApiService
) : BaseDataSource(), CarRepository {
    override suspend fun getCar(): Result<List<CarItem>> {
        val result = getResult {
            carApiService.getCars().let {
                it
            }
        }
        if (result.status == Result.Status.SUCCESS && result.data != null){

        }
        return result
    }
}
package com.example.demoapp.data.repository

import androidx.lifecycle.LiveData
import com.example.demoapp.data.baseRepository.BaseDataSource
import com.example.demoapp.data.local.HouseDao
import com.example.demoapp.data.remote.HouseApiService
import com.example.demoapp.domain.model.House
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.domain.model.localHouse.LocalHouse
import com.example.demoapp.domain.repository.HouseRepository
import com.example.demoapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


import javax.inject.Inject

import android.util.Log

class HouseRepositoryImpl @Inject constructor(
    private val houseDao: HouseDao,
    private val houseApiService: HouseApiService
) : BaseDataSource(), HouseRepository {

    override suspend fun getHouse(location: String): Result<House> {
        val result = getResult {
            houseApiService.getHouse(location)
        }

        // Save the data to the Room database if the API call was successful
        if (result.status == Result.Status.SUCCESS && result.data != null) {

            val houses = result.data.results
            Log.d("HouseRepositoryImpl", "Fetched ${houses.size} houses from the API.")

            try {
                houseDao.insertAll(houses.map { it })
                Log.d("HouseRepositoryImpl", "Successfully inserted ${houses.size} houses into the database.")
            } catch (e: Exception) {
                Log.e("HouseRepositoryImpl", "Error inserting houses into the database: ${e.message}")
            }
        } else {
            Log.e("HouseRepositoryImpl", "API call failed or returned no data.")
        }
        return result
    }

    override suspend fun searchHouses(query: String): Result<List<ResultHouses>> {
        return houseDao.getSearchResult(query).let {
            Log.d("HouseRepositoryImpl", "Search query '$query' returned ${it.size} results.")
            Result.success(it)
        }
    }

    override suspend fun getFilteredHouses(
        minPrice: Int?,
        maxPrice: Int?,
        bedrooms: Int?,
        bathrooms: Int?,
        streetAddress: String
    ): Result<List<ResultHouses>> {
        return houseDao.getFilteredHouses(minPrice, maxPrice, bedrooms, bathrooms, streetAddress).let {
            Result.success(it)
        }
    }

    override suspend fun getAllHousesCached(): Result<List<ResultHouses>> {
        return houseDao.getAllHouses().let {
            Log.d("HouseRepositoryImpl", "Loaded ${it.size} cached houses from the database.")
            Result.success(it)
        }
    }

    override suspend fun addHouse(resultHouse: ResultHouses): Result<Unit> {
        return try {
            houseDao.insertHouse(resultHouse)
            Log.d("HouseRepositoryImpl", "Successfully inserted a house into the database.")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("HouseRepositoryImpl", "Error inserting house: ${e.message}")
            Result.error(e.message ?: "Error inserting house")
        }
    }
}

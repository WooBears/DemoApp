package com.example.demoapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.demoapp.domain.model.House
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.domain.model.localHouse.LocalHouse
import com.example.demoapp.util.Result

interface HouseRepository {

    suspend fun getHouse(location: String) : Result<House>

    suspend fun getAllHousesCached() :  Result<List<ResultHouses>>

    suspend fun searchHouses(query: String) : Result<List<ResultHouses>>

    suspend fun getFilteredHouses(minPrice: Int?,
                                  maxPrice: Int?,
                                  bedrooms: Int?,
                                  bathrooms: Int?,
                                  streetAddress: String) : Result<List<ResultHouses>>

    // New method
    suspend fun addHouse(resultHouse: ResultHouses) : Result<Unit>
}
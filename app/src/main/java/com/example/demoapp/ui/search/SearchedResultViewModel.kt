package com.example.demoapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.domain.repository.HouseRepository
import com.example.demoapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class SearchedResultViewModel  @Inject constructor(
    private val repository: HouseRepository
) : ViewModel() {

    fun getSearchHouse(query: String) : LiveData<Result<List<ResultHouses>>>{
        return liveData (Dispatchers.IO){
            try {
                val result = repository.searchHouses(query)
                emit(result)
            }catch (e: Exception){
                emit(Result.error(e.message ?: "Unknown error"))
            }
        }
    }

    fun getFilteredHouses(
        minPrice: Int?,
        maxPrice: Int?,
        bedrooms: Int?,
        bathrooms: Int?,
        streetAddress: String
    ): LiveData<Result<List<ResultHouses>>> {
        return liveData (Dispatchers.IO){
            try {
                val result = repository.getFilteredHouses(minPrice,maxPrice, bedrooms, bathrooms, streetAddress)
                emit(result)
            }catch (e: Exception){
                emit(Result.error(e.message ?: "Unknown error"))
            }

        }
    }
}
package com.example.demoapp.ui.realEsate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.demoapp.domain.model.House
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.domain.repository.HouseRepository
import com.example.demoapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RealEstateViewModel @Inject constructor(
    private val repository: HouseRepository
) : ViewModel(){

    fun getHouses(location: String) : LiveData<Result<House>> {
        return liveData (Dispatchers.IO){
            try {
                val result = repository.getHouse(location)
                emit(result)
            }catch (e: Exception){
                emit(Result.error(e.message ?: "Unknown error"))
            }
        }
    }

    fun getAllHousesCached() :  LiveData<Result<List<ResultHouses>>> {
        return liveData (Dispatchers.IO){
            try {
                val result = repository.getAllHousesCached()
                emit(result)
            }catch (e: Exception){
                emit(Result.error(e.message ?: "Unknown error"))
            }
        }
    }
}
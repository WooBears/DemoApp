package com.example.demoapp.ui.vehicle

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.demoapp.domain.model.carModel.Car
import com.example.demoapp.domain.model.carModel.CarItem
import com.example.demoapp.domain.repository.CarRepository
import com.example.demoapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {

    fun getCar(): LiveData<Result<List<CarItem>>> {
        return liveData(Dispatchers.IO) {
            val result = carRepository.getCar()
            emit(result)
        }
    }
}
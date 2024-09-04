package com.example.demoapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.domain.model.localHouse.LocalHouse
import com.example.demoapp.domain.repository.HouseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DashboardViewModel : ViewModel() {
    private val _houses = MutableLiveData<MutableList<ResultHouses>>(mutableListOf())
    val houses: MutableLiveData<MutableList<ResultHouses>> get() = _houses

    fun addHouse(house: LocalHouse) {
        _houses.value?.let {
            it.add(mapLocalHouseToResultHouses(house))
            _houses.postValue(it)
        }
    }

    private fun mapLocalHouseToResultHouses(localHouse: LocalHouse): ResultHouses {
        return ResultHouses(
            id = localHouse.id.toInt(), // Converting Long to Int if needed
            bathrooms = localHouse.bathrooms,
            bedrooms = localHouse.bedrooms,
            city = localHouse.city,
            country = localHouse.country,
            currency = localHouse.currency,
            datePriceChanged = localHouse.datePriceChanged,
            daysOnZillow = localHouse.daysOnZillow,
            group_type = localHouse.group_type,
            homeStatus = localHouse.homeStatus,
            homeStatusForHDP = localHouse.homeStatusForHDP,
            homeType = localHouse.homeType,
            imgSrc = localHouse.imgSrc,
            isFeatured = localHouse.isFeatured,
            isNonOwnerOccupied = localHouse.isNonOwnerOccupied,
            isPreforeclosureAuction = localHouse.isPreforeclosureAuction,
            isPremierBuilder = localHouse.isPremierBuilder,
            isShowcaseListing = localHouse.isShowcaseListing,
            isUnmappable = localHouse.isUnmappable,
            isZillowOwned = localHouse.isZillowOwned,
            latitude = localHouse.latitude,
            livingArea = localHouse.livingArea,
            longitude = localHouse.longitude,
            lotAreaUnit = localHouse.lotAreaUnit,
            lotAreaValue = localHouse.lotAreaValue,
            newConstructionType = localHouse.newConstructionType,
            openHouse = localHouse.openHouse,
            price = localHouse.price,
            priceChange = localHouse.priceChange,
            priceForHDP = localHouse.priceForHDP,
            priceReduction = localHouse.priceReduction,
            priceSuffix = localHouse.priceSuffix,
            providerListingID = localHouse.providerListingID,
            rentZestimate = localHouse.rentZestimate,
            shouldHighlight = localHouse.shouldHighlight,
            state = localHouse.state,
            streetAddress = localHouse.streetAddress,
            taxAssessedValue = localHouse.taxAssessedValue,
            unit = localHouse.unit,
            videoCount = localHouse.videoCount,
            zestimate = localHouse.zestimate,
            zipcode = localHouse.zipcode,
            zpid = localHouse.zpid
        )
    }

}
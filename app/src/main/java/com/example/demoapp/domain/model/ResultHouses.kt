package com.example.demoapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "ResultHouses", indices = [Index(value = ["id"], unique = true)])

@Parcelize
data class ResultHouses(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val bathrooms: Int,
    val bedrooms: Int,
    val city: String?,
    val country: String?,
    val currency: String?,
    val datePriceChanged: Long,
    val daysOnZillow: Int,
    val group_type: String?,
    val homeStatus: String?,
    val homeStatusForHDP: String?,
    val homeType: String?,
    val imgSrc: String?,
    val isFeatured: Boolean?,
    val isNonOwnerOccupied: Boolean,
    val isPreforeclosureAuction: Boolean,
    val isPremierBuilder: Boolean,
    val isShowcaseListing: Boolean,
    val isUnmappable: Boolean,
    val isZillowOwned: Boolean,
    val latitude: Double,
    val livingArea: Int,
    val longitude: Double,
    val lotAreaUnit: String?,
    val lotAreaValue: Double,
    val newConstructionType: String?,
    val openHouse: String?,
    val price: Int,
    val priceChange: Int,
    val priceForHDP: Int,
    val priceReduction: String?,
    val priceSuffix: String?,
    val providerListingID: String?,
    val rentZestimate: Int,
    val shouldHighlight: Boolean,
    val state: String?,
    val streetAddress: String?,
    val taxAssessedValue: Int,
    val unit: String?,
    val videoCount: Int,
    val zestimate: Int,
    val zipcode: String?,
    val zpid: Int
) : Parcelable
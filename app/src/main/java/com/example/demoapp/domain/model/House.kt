package com.example.demoapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize


@Parcelize
data class House(
    val results: List<ResultHouses>,
    val resultsPerPage: Int,
    val totalPages: Int,
    val totalResultCount: Int
) : Parcelable
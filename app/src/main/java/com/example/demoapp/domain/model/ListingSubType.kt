package com.example.demoapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListingSubType(
    val is_FSBA: Boolean,
    val is_bankOwned: Boolean,
    val is_newHome: Boolean,
    val is_openHouse: Boolean
) :Parcelable
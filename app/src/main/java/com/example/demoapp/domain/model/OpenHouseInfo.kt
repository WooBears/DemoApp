package com.example.demoapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OpenHouseInfo(
    val open_house_showing: List<OpenHouseShowing>
) : Parcelable
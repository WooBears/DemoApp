package com.example.demoapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OpenHouseShowing(
    val open_house_end: Long,
    val open_house_start: Long
) : Parcelable
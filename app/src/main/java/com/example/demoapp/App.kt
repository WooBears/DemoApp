package com.example.demoapp

import android.app.Application
import androidx.room.Room
import com.example.demoapp.data.local.HouseDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

}
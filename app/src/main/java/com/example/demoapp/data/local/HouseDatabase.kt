package com.example.demoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demoapp.domain.model.House
import com.example.demoapp.domain.model.ResultHouses

@Database(entities = [ResultHouses::class], version = 1)
abstract class HouseDatabase : RoomDatabase() {
    abstract fun houseDao() : HouseDao
}
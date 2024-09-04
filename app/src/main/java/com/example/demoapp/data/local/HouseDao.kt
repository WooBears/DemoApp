package com.example.demoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.demoapp.domain.model.ResultHouses
import com.example.demoapp.domain.model.localHouse.LocalHouse

@Dao
interface HouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(resultHouses: ResultHouses)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(houses: List<ResultHouses>)

    @Transaction
    fun updateRecord(resultHouses: ResultHouses){
        deleteAllRecords()
        upsert(resultHouses)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHouse(resultHouse: ResultHouses)

    @Query("SELECT * FROM ResultHouses WHERE city || unit LIKE '%' || :search || '%'")
    fun getSearchResult(search: String) :List<ResultHouses>

    @Query("""
        SELECT * FROM ResultHouses 
        WHERE (:minPrice IS NULL OR price >= :minPrice) 
        AND (:maxPrice IS NULL OR price <= :maxPrice) 
        AND (:bedrooms IS NULL OR bedrooms = :bedrooms) 
        AND (:bathrooms IS NULL OR bathrooms = :bathrooms) 
        AND (streetAddress LIKE '%' || :streetAddress || '%')
    """)
    fun getFilteredHouses(
        minPrice: Int?,
        maxPrice: Int?,
        bedrooms: Int?,
        bathrooms: Int?,
        streetAddress: String
    ): List<ResultHouses>

    @Query("SELECT * FROM ResultHouses")
    fun getAllHouses() :  List<ResultHouses>

    @Query("DELETE FROM  ResultHouses")
    fun deleteAllRecords()
}



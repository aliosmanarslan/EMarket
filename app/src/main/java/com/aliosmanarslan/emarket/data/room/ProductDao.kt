package com.aliosmanarslan.emarket.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aliosmanarslan.emarket.data.Product

@Dao
interface ProductDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg countries: Product) : List<Long>

    //Insert -> INSERT INTO
    // suspend -> coroutine, pause & resume
    // vararg -> multiple country objects
    // List<Long> -> primary keys


    @Query("SELECT * FROM product")
    suspend fun getAllCountries() : List<Product>

    @Query("SELECT * FROM product WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Product

    @Query("DELETE FROM product")
    suspend fun deleteAllCountries()


}
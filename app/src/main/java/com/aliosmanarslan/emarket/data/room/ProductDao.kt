package com.aliosmanarslan.emarket.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aliosmanarslan.emarket.data.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(vararg products: Product) : List<Long>

    @Query("SELECT * FROM product")
    suspend fun getAllProducts() : List<Product>

    @Query("SELECT * FROM product WHERE uuid = :productId")
    suspend fun getProducts(productId : Int) : Product

    @Query("DELETE FROM product")
    suspend fun deleteAllProducts()


}
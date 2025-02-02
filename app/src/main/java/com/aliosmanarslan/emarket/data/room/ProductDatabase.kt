package com.aliosmanarslan.emarket.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aliosmanarslan.emarket.data.Product

@Database(entities = arrayOf(Product::class),version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao() : ProductDao

    //Singleton

    companion object {

        @Volatile private var instance : ProductDatabase? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,ProductDatabase::class.java,"productdatabase"
        ).build()
    }
}
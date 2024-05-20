package com.aliosmanarslan.emarket.service

import com.aliosmanarslan.emarket.data.Product
import io.reactivex.Single
import retrofit2.http.GET

interface ProductAPI {

    @GET("products")
    fun getCountries(): Single<List<Product>>


}
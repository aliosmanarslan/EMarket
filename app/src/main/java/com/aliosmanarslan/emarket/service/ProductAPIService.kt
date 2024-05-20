package com.aliosmanarslan.emarket.service

import com.aliosmanarslan.emarket.data.Product
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class ProductAPIService {


    private val BASE_URL = "https://5fc9346b2af77700165ae514.mockapi.io/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ProductAPI::class.java)

    fun getData() : Single<List<Product>> {
        return api.getCountries()
    }


}
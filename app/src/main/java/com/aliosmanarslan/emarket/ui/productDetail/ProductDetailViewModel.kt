package com.aliosmanarslan.emarket.ui.productDetail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aliosmanarslan.emarket.base.BaseViewModel
import com.aliosmanarslan.emarket.data.Product
import com.aliosmanarslan.emarket.data.room.ProductDatabase
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : BaseViewModel(application) {

    val productLiveData = MutableLiveData<Product>()

    fun getDataFromRoom(uuid: Int) {
        launch {

            val dao = ProductDatabase(getApplication()).productDao()
            val product = dao.getProducts(uuid)
            productLiveData.value = product

        }

    }
}
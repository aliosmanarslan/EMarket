package com.aliosmanarslan.emarket.ui.product

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.aliosmanarslan.emarket.base.BaseViewModel
import com.aliosmanarslan.emarket.data.Product
import com.aliosmanarslan.emarket.data.room.ProductDatabase
import com.aliosmanarslan.emarket.service.ProductAPIService
import com.aliosmanarslan.emarket.utils.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : BaseViewModel(application) {

    private val productApiService = ProductAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val products = MutableLiveData<List<Product>>()
    val productError = MutableLiveData<Boolean>()
    val productLoading = MutableLiveData<Boolean>()

    fun refreshData() {

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }

    }

    fun refreshFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        productLoading.value = true
        launch {
            val products = ProductDatabase(getApplication()).productDao().getAllProducts()
            showProducts(products)
            Toast.makeText(getApplication(),"Products From SQLite", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI() {
        productLoading.value = true

        disposable.add(
            productApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>(){
                    override fun onSuccess(t: List<Product>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(),"Products From API", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        productLoading.value = false
                        productError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showProducts(productList: List<Product>) {
        products.value = productList
        productError.value = false
        productLoading.value = false
    }

    private fun storeInSQLite(list: List<Product>) {
        launch {
            val dao = ProductDatabase(getApplication()).productDao()
            dao.deleteAllProducts()
            val listLong = dao.insertProduct(*list.toTypedArray()) // -> list -> individual
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }

            showProducts(list)
        }

        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }


}
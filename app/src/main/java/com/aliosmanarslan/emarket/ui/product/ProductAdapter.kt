package com.aliosmanarslan.emarket.ui.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.emarket.R
import com.aliosmanarslan.emarket.data.Product
import com.aliosmanarslan.emarket.data.room.ProductDatabase
import com.aliosmanarslan.emarket.databinding.ItemProductBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductAdapter(val countryList: ArrayList<Product>, val context: Context): RecyclerView.Adapter<ProductAdapter.CountryViewHolder>(){



    class CountryViewHolder(var view: ItemProductBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_country,parent,false)
        val view = DataBindingUtil.inflate<ItemProductBinding>(inflater,
            R.layout.item_product,parent,false)
        return CountryViewHolder(view)

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.country = countryList[position]

        holder.view.root.setOnClickListener {
            val product = countryList[position]
            val action = ProductFragmentDirections.actionFeedFragmentToCountryFragment(product.uuid)
            Navigation.findNavController(holder.view.root).navigate(action)
        }

        holder.view.addToCartButton.setOnClickListener {
            val product = countryList[position]
            GlobalScope.launch(Dispatchers.IO) {
                ProductDatabase(context).countryDao().insertAll(product)
            }
        }


    }


    fun updateCountryList(newCountryList: List<Product>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

}
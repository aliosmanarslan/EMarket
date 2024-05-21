package com.aliosmanarslan.emarket.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.emarket.R
import com.aliosmanarslan.emarket.data.Product
import com.aliosmanarslan.emarket.databinding.ItemCartBinding
import com.aliosmanarslan.emarket.databinding.ItemFavoriteBinding
import com.aliosmanarslan.emarket.ui.cart.CartAdapter
import com.aliosmanarslan.emarket.ui.product.ProductFragmentDirections
import com.aliosmanarslan.emarket.utils.CartUpdateListener
import com.aliosmanarslan.emarket.utils.Constant

class FavoriteAdapter(val favList: List<Product>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    class FavoriteHolder(var view: ItemFavoriteBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemFavoriteBinding>(inflater,
            R.layout.item_favorite,parent,false)
        return FavoriteAdapter.FavoriteHolder(view)

    }

    override fun getItemCount(): Int {
        return favList.size
    }


    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.view.product = favList[position]
        holder.view.root.setOnClickListener {
            val product = favList[position]
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToCountryFragment(product.uuid)
            Navigation.findNavController(holder.view.root).navigate(action)
        }

    }
}
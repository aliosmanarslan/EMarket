package com.aliosmanarslan.emarket.ui.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.emarket.R
import com.aliosmanarslan.emarket.data.Product
import com.aliosmanarslan.emarket.databinding.ItemProductBinding
import com.aliosmanarslan.emarket.utils.Constant.cartList
import com.aliosmanarslan.emarket.utils.Constant.favList

class ProductAdapter(private val productsList: ArrayList<Product>, private val context: Context) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var filteredProductsList: ArrayList<Product> = ArrayList(productsList)

    class ProductViewHolder(var view: ItemProductBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemProductBinding>(inflater, R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredProductsList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.view.product = filteredProductsList[position]

        holder.view.root.setOnClickListener {
            val product = filteredProductsList[position]
            val action = ProductFragmentDirections.actionFeedFragmentToCountryFragment(product.uuid)
            Navigation.findNavController(holder.view.root).navigate(action)
        }

        holder.view.addToCartButton.setOnClickListener {
            val product = filteredProductsList[position]
            addOrUpdateProduct(product)
        }

        holder.view.favoriteIcon.setOnClickListener {
            val product = filteredProductsList[position]
            if (product.isFavorite) {
                removeFromFavorites(product)
                holder.view.favoriteIcon.setImageResource(R.drawable.star_2)
            } else {
                addToFavorites(product)
                holder.view.favoriteIcon.setImageResource(R.drawable.star_1)
            }
            product.isFavorite = !product.isFavorite
            notifyItemChanged(position)
        }
    }

    fun addOrUpdateProduct(newProduct: Product) {
        val existingProduct = cartList.find { it.id == newProduct.id }
        if (existingProduct != null) {
            existingProduct.adet = existingProduct.adet!! + 1
            notifyItemChanged(cartList.indexOf(existingProduct))
        } else {
            newProduct.adet = 1
            cartList.add(newProduct)
            notifyItemInserted(productsList.size - 1)
        }
    }

    fun updateProductList(newProductList: List<Product>) {
        productsList.clear()
        productsList.addAll(newProductList)
        filter("")
    }

    fun filter(query: String) {
        filteredProductsList = if (query.isEmpty()) {
            ArrayList(productsList)
        } else {
            val resultList = ArrayList<Product>()
            for (product in productsList) {
                if (product.name!!.contains(query, true)) {
                    resultList.add(product)
                }
            }
            resultList
        }
        notifyDataSetChanged()
    }

    fun addToFavorites(product: Product) {
        favList.add(product)
    }

    fun removeFromFavorites(product: Product) {
        favList.remove(product)
    }
}

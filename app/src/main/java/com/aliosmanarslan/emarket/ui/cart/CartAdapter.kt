package com.aliosmanarslan.emarket.ui.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aliosmanarslan.emarket.R
import com.aliosmanarslan.emarket.data.Product
import com.aliosmanarslan.emarket.databinding.ItemCartBinding
import com.aliosmanarslan.emarket.databinding.ItemProductBinding
import com.aliosmanarslan.emarket.ui.product.ProductAdapter
import com.aliosmanarslan.emarket.utils.CartUpdateListener
import com.aliosmanarslan.emarket.utils.Constant.cartList

class CartAdapter(val cartList: List<Product>, val listener: CartUpdateListener) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(var view: ItemCartBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCartBinding>(inflater,
            R.layout.item_cart,parent,false)
        return CartAdapter.CartViewHolder(view)

    }

    override fun getItemCount(): Int {
        return cartList.size
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.view.product = cartList[position]
        holder.view.textView.text = cartList[position].name
        holder.view.textView2.text = cartList[position].price.toString()
        holder.view.textView3.text = cartList[position].adet.toString()

        holder.view.button2.setOnClickListener {
            cartList[position].adet = cartList[position].adet?.plus(1)
            notifyDataSetChanged()
            listener.updateTotalPrice()
        }
        holder.view.button3.setOnClickListener {
            if (cartList.size >= 1){
                cartList[position].adet = cartList[position].adet?.minus(1)
                notifyDataSetChanged()
                listener.updateTotalPrice()
            }

        }

    }
}

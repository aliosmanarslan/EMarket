package com.aliosmanarslan.emarket.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aliosmanarslan.emarket.MainActivity
import com.aliosmanarslan.emarket.R
import com.aliosmanarslan.emarket.databinding.FragmentCartBinding
import com.aliosmanarslan.emarket.ui.product.ProductAdapter
import com.aliosmanarslan.emarket.ui.product.ProductViewModel
import com.aliosmanarslan.emarket.utils.CartUpdateListener
import com.aliosmanarslan.emarket.utils.Constant.cartList


class CartFragment : Fragment(), CartUpdateListener {

    private lateinit var cartBinding: FragmentCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartAdapter = CartAdapter(cartList, this as CartUpdateListener)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartBinding = FragmentCartBinding.inflate(inflater, container, false)

        return cartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartBinding.rvCart.layoutManager = LinearLayoutManager(context)
        cartBinding.rvCart.adapter = cartAdapter
        setToolbarName("Cart Screen")
        updateTotalPrice()
    }

    override fun updateTotalPrice() {
        var totalPrice = 0.0
        for (product in cartList) {
            val price = product.price?.toDoubleOrNull() ?: 0.0
            totalPrice += price * product.adet!!
        }
        totalPrice = maxOf(totalPrice, 0.0)
        cartBinding.textView4.text = "$%.2f".format(totalPrice)
    }

    fun setToolbarName(
        name: String
    ) {
        val activity = activity as MainActivity
        activity.setToolbarName(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        setToolbarName("")
    }
}
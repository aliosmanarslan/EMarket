package com.aliosmanarslan.emarket.ui.product

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.aliosmanarslan.emarket.data.room.ProductDatabase
import com.aliosmanarslan.emarket.databinding.FragmentProductBinding

class ProductFragment : Fragment() {

    private lateinit var viewModel : ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productDatabase: ProductDatabase
    private lateinit var bindingProduct: FragmentProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productDatabase = ProductDatabase.invoke(requireContext())
        productAdapter = ProductAdapter(arrayListOf(), requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingProduct = FragmentProductBinding.inflate(inflater, container, false)
        return bindingProduct.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        viewModel.refreshData()

        bindingProduct.productList.layoutManager = GridLayoutManager(context, 2)
        bindingProduct.productList.adapter = productAdapter

        bindingProduct.swipeRefreshLayout.setOnRefreshListener {
            bindingProduct.productList.visibility = View.GONE
            bindingProduct.productError.visibility = View.GONE
            bindingProduct.productLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            bindingProduct.swipeRefreshLayout.isRefreshing = false
        }

        bindingProduct.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                productAdapter.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.products.observe(viewLifecycleOwner, Observer { products ->
            products?.let {
                bindingProduct.productList.visibility = View.VISIBLE
                productAdapter.updateProductList(products)
            }
        })

        viewModel.productError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it) {
                    bindingProduct.productError.visibility = View.VISIBLE
                } else {
                    bindingProduct.productError.visibility = View.GONE
                }
            }
        })

        viewModel.productLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it) {
                    bindingProduct.productLoading.visibility = View.VISIBLE
                    bindingProduct.productList.visibility = View.GONE
                    bindingProduct.productError.visibility = View.GONE
                } else {
                    bindingProduct.productLoading.visibility = View.GONE
                }
            }
        })
    }
}

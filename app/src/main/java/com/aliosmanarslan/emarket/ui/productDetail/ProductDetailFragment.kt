package com.aliosmanarslan.emarket.ui.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.aliosmanarslan.emarket.MainActivity
import com.aliosmanarslan.emarket.R
import com.aliosmanarslan.emarket.data.Product
import com.aliosmanarslan.emarket.databinding.FragmentProductDetailBinding
import com.aliosmanarslan.emarket.utils.Constant


class ProductDetailFragment : Fragment() {

    private lateinit var viewModel : ProductDetailViewModel
    private var productUuid = 0
    private lateinit var pdBinding : FragmentProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        pdBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_detail,container,false)
        return pdBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = ProductDetailFragmentArgs.fromBundle(it)
            productUuid = args.productUuid
        }


        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        viewModel.getDataFromRoom(productUuid)

        val activity = activity as MainActivity
        val action = ProductDetailFragmentDirections.actionCountryFragmentToFeedFragment()
        activity.showToolBarNavigate()
        activity.navAction(action)

        pdBinding.button.setOnClickListener {
            addOrUpdateProduct(viewModel.productLiveData.value!!)
        }

        observeLiveData()
    }

    fun addOrUpdateProduct(newProduct: Product) {
        val existingProduct = Constant.cartList.find { it.id == newProduct.id }
        if (existingProduct != null) {
            existingProduct.adet = existingProduct.adet!! + 1
        } else {
            newProduct.adet = 1
            Constant.cartList.add(newProduct)
        }
    }


    private fun observeLiveData() {
        viewModel.productLiveData.observe(viewLifecycleOwner, Observer { it->
            it?.let {
                pdBinding.selectedProduct = it
                setToolbarName(it.name.toString())

            }
        })
    }


    fun setToolbarName(
        name: String
    ) {
        val activity = activity as MainActivity
        activity.setToolbarName(name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setToolbarName("")
        val activity = activity as MainActivity
        activity.hideToolBarNavigate()
    }

}
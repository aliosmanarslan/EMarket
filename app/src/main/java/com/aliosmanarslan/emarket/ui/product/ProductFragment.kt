package com.aliosmanarslan.emarket.ui.product

import android.os.Bundle
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
    private lateinit var countryAdapter: ProductAdapter
    private lateinit var countryDatabase: ProductDatabase
    private lateinit var bindingProduct: FragmentProductBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryDatabase = ProductDatabase.invoke(requireContext())
        countryAdapter = ProductAdapter(arrayListOf(), requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingProduct = FragmentProductBinding.inflate(inflater, container, false)

        return bindingProduct.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        viewModel.refreshData()

        bindingProduct.countryList.layoutManager = GridLayoutManager(context, 2)
        bindingProduct.countryList.adapter = countryAdapter




//        button.setOnClickListener {
//            val action = FeedFragmentDirections.action_countryFragment_to_feedFragment()
//            Navigation.findNavController(it).navigate(action)
//        }



        bindingProduct.swipeRefreshLayout.setOnRefreshListener {
            bindingProduct.countryList.visibility = View.GONE
            bindingProduct.countryError.visibility = View.GONE
            bindingProduct.countryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            bindingProduct.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries ->

            countries?.let {
                bindingProduct.countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }

        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if(it) {
                    bindingProduct.countryError.visibility = View.VISIBLE
                } else {
                    bindingProduct.countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it) {
                    bindingProduct.countryLoading.visibility = View.VISIBLE
                    bindingProduct.countryList.visibility = View.GONE
                    bindingProduct.countryError.visibility = View.GONE
                } else {
                    bindingProduct.countryLoading.visibility = View.GONE
                }
            }
        })
    }


}
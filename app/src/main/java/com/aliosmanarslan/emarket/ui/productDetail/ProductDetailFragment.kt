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
import com.aliosmanarslan.emarket.databinding.FragmentProductDetailBinding
import com.aliosmanarslan.emarket.ui.productDetail.ProductDetailFragmentArgs.Companion.fromBundle


class ProductDetailFragment : Fragment() {

    private lateinit var viewModel : ProductDetailViewModel
    private var countryUuid = 0
    private lateinit var dataBinding : FragmentProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val args = ProductDetailFragmentArgs.fromBundle(it)
            countryUuid = args.countryUuid
        }


        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        val activity = activity as MainActivity
        val action = ProductDetailFragmentDirections.actionCountryFragmentToFeedFragment()
        activity.showToolBarNavigate()
        activity.asda(action)

        observeLiveData()
    }



    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
            country?.let {
                dataBinding.selectedCountry = country
                setToolbarName(it.name.toString())
                /*
                countryName.text = country.countryName
                countryCapital.text = country.countryCapital
                countryCurrency.text = country.countryCurrency
                countryLanguage.text = country.countryLanguage
                countryRegion.text = country.countryRegion
                context?.let {
                    countryImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))
                }

                 */
            }
        })
    }


    fun setToolbarName(
        name: String
    ) {
        val activity = activity as MainActivity
        activity.setToolbarName(name)
    }


}
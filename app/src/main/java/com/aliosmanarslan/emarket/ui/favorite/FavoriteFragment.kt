package com.aliosmanarslan.emarket.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aliosmanarslan.emarket.MainActivity
import com.aliosmanarslan.emarket.R
import com.aliosmanarslan.emarket.databinding.FragmentFavoriteBinding
import com.aliosmanarslan.emarket.utils.Constant
import com.aliosmanarslan.emarket.utils.Constant.favList


class FavoriteFragment : Fragment() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favBinding : FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteAdapter = FavoriteAdapter(favList)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        favBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return favBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favBinding.rvFav.layoutManager = LinearLayoutManager(context)
        favBinding.rvFav.adapter = favoriteAdapter
        setToolbarName("Favorite Screen")
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
    }
}
package com.aliosmanarslan.emarket

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.aliosmanarslan.emarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // NavController örneğini oluştur
        navController = Navigation.findNavController(this, R.id.fragment)
        // Setup bottom navigation with navController
        val bottomNavView = binding.bottomNavMain
        NavigationUI.setupWithNavController(bottomNavView, navController)

        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    navController.navigate(R.id.feedFragment)
                    true
                }
                R.id.menu_cart -> {
                    navController.navigate(R.id.cartFragment)
                    true
                }
                R.id.menu_favorite -> {
                    navController.navigate(R.id.favoriteFragment)
                    true
                }
//                R.id.menu_profile -> {
//                    navController.navigate(R.id.profileFragment)
//                    true
//                }
                else -> false
            }
        }

    }


    fun navAction(action: NavDirections){
        binding.tvToolbarImage.setOnClickListener {
            navController.navigate(action)
        }
    }

    fun setToolbarName(
        name: String
    ) {
        if (name == "")
            binding.tvToolbarTitle.text = ""
        else {
            binding.tvToolbarTitle.text = name
        }
    }

    /**
     * Hide the Navigation Bar from the page
     */
    fun showToolBarNavigate() {
        binding.tvToolbarImage.visibility = View.VISIBLE
    }

    /**
     * Show Navigation Bar on page
     */
    fun hideToolBarNavigate() {
        binding.tvToolbarImage.visibility = View.GONE
    }


}
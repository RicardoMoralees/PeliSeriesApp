package com.ricardo.peliseriesapp.view.activities

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ricardo.peliseriesapp.R
import com.ricardo.peliseriesapp.databinding.ActivityMainBinding
import com.ricardo.peliseriesapp.utils.Constants
import com.ricardo.peliseriesapp.utils.Constants.Companion.SELECTED_FRAGMENT
import com.ricardo.peliseriesapp.viewmodel.PeliculaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var peliculaViewModel: PeliculaViewModel
    lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        peliculaViewModel = ViewModelProvider.NewInstanceFactory().create(PeliculaViewModel::class.java)

        navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

    }
}
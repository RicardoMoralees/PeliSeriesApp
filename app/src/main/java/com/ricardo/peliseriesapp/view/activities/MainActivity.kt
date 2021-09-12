package com.ricardo.peliseriesapp.view.activities

import android.os.Bundle
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
import com.ricardo.peliseriesapp.viewmodel.PeliculaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var peliculaViewModel: PeliculaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        peliculaViewModel = ViewModelProvider.NewInstanceFactory().create(PeliculaViewModel::class.java)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_peliculas, R.id.navigation_series
            )
        )
        navView.setupWithNavController(navController)
        //binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
    }
}
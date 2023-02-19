package com.graphicless.cricketapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ActivityMainBinding
import com.graphicless.cricketapp.network.NetworkConnectivityCallback
import com.graphicless.cricketapp.network.NetworkConnectivityChecker
import com.graphicless.cricketapp.viewmodel.CricketViewModel


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), NetworkConnectivityCallback {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CricketViewModel by viewModels()
    private lateinit var navController: NavController

    private lateinit var networkConnectivityChecker : NetworkConnectivityChecker

    private var shouldInsertData = true
    private var bottomNavigationViewHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Get the height of the BottomNavigationView
        bottomNavigationViewHeight = binding.bottomNavMenu.height

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        binding.bottomNavMenu.setupWithNavController(navController)

        // Check internet for the first time when app starts
        if (!isNetworkAvailable()) {
            viewModel.networkLost()
        }else{
            viewModel.networkAvailable()
        }

        // Check when internet states will change
        networkConnectivityChecker = NetworkConnectivityChecker(this)
        networkConnectivityChecker.setCallback(this)
        networkConnectivityChecker.start()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun insertDataFromApiToLocalDatabase() {
//        viewModel.insertContinents()
//        viewModel.insertCountries()
//        viewModel.insertLeagues()

        viewModel.insertFixtures()
        viewModel.insertTeams()
        viewModel.insertVenues()
        viewModel.insertStages()
        viewModel.insertSeasons()
        viewModel.insertOfficials()
    }

    override fun onNetworkLost() {
        Log.d(TAG, "onNetworkLost: ")
        viewModel.networkLost()
        val snackbar = Snackbar.make(
            binding.bottomNavMenu,
            "No network connectivity",
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.anchorView = binding.bottomNavMenu
        snackbar.show()
    }

    override fun onNetworkAvailable() {
        Log.d(TAG, "onNetworkAvailable: ")
        viewModel.networkAvailable()
        if(shouldInsertData){
            shouldInsertData = false
            insertDataFromApiToLocalDatabase()
        }else{
            val snackbar = Snackbar.make(
                binding.bottomNavMenu,
                "Network connectivity restored",
                Snackbar.LENGTH_SHORT
            )
            snackbar.anchorView = binding.bottomNavMenu
            snackbar.show()
        }
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.getActiveNetworkInfo()
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}


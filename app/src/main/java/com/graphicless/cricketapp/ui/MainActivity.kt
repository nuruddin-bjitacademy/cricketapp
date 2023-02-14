package com.graphicless.cricketapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ActivityMainBinding
import com.graphicless.cricketapp.utils.MyConstants
import com.graphicless.cricketapp.viewmodel.CricketViewModel

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CricketViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Log.d(TAG, "api key: ${MyConstants.API_KEY}")

        insertDataFromApiToLocalDatabase()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        viewModel.continents.observe(this) {
            Log.d(TAG, "continents: $it")
        }

        /*viewModel.countries.observe(this){
//            val recyclerViewState = binding.rvCountry.layoutManager?.onSaveInstanceState()
//            binding.rvCountry.layoutManager?.onRestoreInstanceState(recyclerViewState)
            val adapter = CountryAdapter(it, this)
            binding.rvCountry.adapter = adapter
        }*/

        viewModel.leagues.observe(this) {
            Log.d(TAG, "leagues: $it")
        }

        viewModel.getContinentName(12).observe(this) {
            Log.d(TAG, "country name : $it")
        }

        viewModel.fixtures.observe(this) {
            Log.d(TAG, "fixtures : $it")
        }


        /*viewModel.getCountryWithContinent.observe(this){
            val adapter = CountryWithContinentAdapter(it)
            binding.rvCountry.adapter = adapter
        }*/

        /*viewModel.getFixtureAndTeam.observe(this){
            val adapter = MatchAdapter(it)
            binding.rvStage.adapter = adapter
        }*/

        /*viewModel.getFixtureAndTeam2.observe(this){
            Log.d(TAG, "test: $it")
        }*/

        /*viewModel.getDistinctStageName.observe(this){
            Log.d(TAG, "stages: $it")
            val adapter = StageAdapter(it, this)
            binding.rvStage.adapter = adapter
        }*/


        /*viewModel.fixtures.observe(this){
            Log.d(TAG, "fixtures: $it")
        }*/

        setupActionBarWithNavController(navController)

        binding.bottomNavMenu.setupWithNavController(navController)
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
}
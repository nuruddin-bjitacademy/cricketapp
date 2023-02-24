package com.graphicless.cricketapp.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.graphicless.cricketapp.R
import com.graphicless.cricketapp.databinding.ActivityMainBinding
import com.graphicless.cricketapp.network.NetworkConnectivityCallback
import com.graphicless.cricketapp.network.NetworkConnectivityChecker
import com.graphicless.cricketapp.utils.NotificationReceiver
import com.graphicless.cricketapp.utils.SharedPreference
import com.graphicless.cricketapp.viewmodel.CricketViewModel
import com.graphicless.cricketapp.viewmodel.NetworkConnectionViewModel
import kotlinx.coroutines.*
import java.time.*
import java.util.*


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), NetworkConnectivityCallback {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CricketViewModel by viewModels()

    //    private val viewModel: CricketViewModel by lazy {
//    ViewModelProvider(this)[CricketViewModel::class.java]
//    }
    private val netWorkConnectionViewModel: NetworkConnectionViewModel by viewModels()
    private lateinit var navController: NavController

    private lateinit var networkConnectivityChecker: NetworkConnectivityChecker

    private var bottomNavigationViewHeight = 0
    private var showNetworkRestore = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.action_bar)))
        binding.bottomNavMenu.setBackgroundColor(getColor(R.color.action_bar))

        // Night Mode Start
        val sharedPreference = SharedPreference()

        if (sharedPreference.getString("theme") != null) {
            val myTheme = sharedPreference.getString("theme")
            if (myTheme == "light")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        // Night Mode End

        if (!sharedPreference.isContain("data_inserted")) {
            insertDataFromApiToLocalDatabase()
            sharedPreference.save("data_inserted", true)
        }
        

        // Get the height of the BottomNavigationView
        bottomNavigationViewHeight = binding.bottomNavMenu.height

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        binding.bottomNavMenu.setupWithNavController(navController)

        // Check internet for the first time when app starts
        if (!isNetworkAvailable()) {
            netWorkConnectionViewModel.networkLost()
        } else {
            netWorkConnectionViewModel.networkAvailable()
        }

        // Check when internet states will change
        networkConnectivityChecker = NetworkConnectivityChecker(this)
        networkConnectivityChecker.setCallback(this)
        networkConnectivityChecker.start()

        

        /*// Update data 24 hours internal
        val updateDataWorkRequest = PeriodicWorkRequest.Builder(
            UpdateDataWorker::class.java,
            24,
            TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "update_data",
            ExistingPeriodicWorkPolicy.KEEP,
            updateDataWorkRequest
        )*/

        viewModel.getAllUpcomingFixture().observe(this){
            // Get the list of matches from your Room database
            val matchList = it
            // Get the current time in milliseconds
            val currentTimeMillis = System.currentTimeMillis()
            for (match in matchList) {

                val utcDateTimeString = match.startTime

                val instant = Instant.parse(utcDateTimeString)

                val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())

                val notificationTime = zonedDateTime.minusMinutes(15)

                if (notificationTime.toInstant().toEpochMilli() > currentTimeMillis) {
                    val delayInMillis =
                        Duration.ofMinutes(15).toMillis() // convert minutes to milliseconds
                    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val notificationIntent = Intent(this, NotificationReceiver::class.java)
                    notificationIntent.putExtra("match_id", match.id)
                    val pendingIntent = PendingIntent.getBroadcast(
                        this,
                        match.id,
                        notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        notificationTime.toInstant().toEpochMilli() - delayInMillis,
                        pendingIntent
                    )
                }
            }

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun insertDataFromApiToLocalDatabase() {
        Log.d(TAG, "insertDataFromApiToLocalDatabase: called")
//        val apiTask = ApiTask()
//        apiTask.execute()
        try {
            lifecycleScope.launch(CoroutineExceptionHandler { _, ex ->
                Log.e(TAG, "insertDataFromApiToLocalDatabase: coroutine exception handler: $ex")
            }) {
                try {
                    viewModel.insertTeamRankings()
                    viewModel.insertCountries()
                    viewModel.insertLeagues()
                    viewModel.insertTeams()
                    viewModel.insertVenues()
                    viewModel.insertStages()
                    viewModel.insertSeasons()
                    viewModel.insertOfficials()
////            viewModel.insertFixtures()
                    viewModel.insertUpcomingFixtures()
                    viewModel.insertPreviousFixtures()
                    viewModel.insertPlayers()
                } catch (exception: Exception) {
                    Log.e(TAG, "insertDataFromApiToLocalDatabase: innner: $exception")
                }

            }
        } catch (exception: java.lang.Exception) {
            Log.e(TAG, "insertDataFromApiToLocalDatabase: $exception")
        }
    }

    override fun onNetworkLost() {
        netWorkConnectionViewModel.networkLost()
    }

    override fun onNetworkAvailable() {
        netWorkConnectionViewModel.networkAvailable()
        if (showNetworkRestore) {
            val snackbar = Snackbar.make(
                binding.bottomNavMenu,
                "Network connectivity restored",
                Snackbar.LENGTH_SHORT
            )
            snackbar.anchorView = binding.bottomNavMenu
            snackbar.show()
        } else {
            showNetworkRestore = true
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.getActiveNetworkInfo()
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}


package com.graphicless.cricketapp.network

import android.app.Application
import android.os.AsyncTask
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class ApiTask : AsyncTask<Void, Void, String>() {

    val viewModel = CricketViewModel(Application())

    override fun doInBackground(vararg params: Void?): String {
        try {
            // Call function 1
            val result1 = viewModel.insertTeamRankings()

            // Call function 2
            val result2 = viewModel.insertCountries()

            // Call function 3
            val result3 = viewModel.insertLeagues()

            // Call function 4
            val result4 = viewModel.insertTeams()

            // Call function 5
            val result5 = viewModel.insertVenues()

            // Call function 6
            val result6 = viewModel.insertStages()

            // Call function 7
            val result7 = viewModel.insertSeasons()

            // Call function 8
            val result8 = viewModel.insertOfficials()

            // Call function 9
            val result9 = viewModel.insertUpcomingFixtures()

            // Call function 10
            val result10 = viewModel.insertPreviousFixtures()

            // Call function 11
            val result11 = viewModel.insertPlayers()

            // Call function 12
            val result12 = "true"

            return result12
        } catch (e: Exception) {
            // Handle any exceptions that may occur
            e.printStackTrace()
            return ""
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        // Handle the result of the task and update the UI as needed
        if (!result.isNullOrEmpty()) {
            // Update the UI with the result
        } else {
            // Handle the error
        }
    }

}

package com.graphicless.cricketapp.utils

import android.app.Application
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.graphicless.cricketapp.viewmodel.CricketViewModel

class UpdateDataWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    val viewModel = CricketViewModel(Application())
    override fun doWork(): Result {
        // Make API call and store the response in Room database
        viewModel.insertTeamRankings()
        viewModel.insertCountries()
        viewModel.insertLeagues()
        viewModel.insertTeams()
        viewModel.insertVenues()
        viewModel.insertStages()
        viewModel.insertSeasons()
        viewModel.insertOfficials()
//            viewModel.insertFixtures()
        viewModel.insertUpcomingFixtures()
        viewModel.insertPreviousFixtures()
        viewModel.insertPlayers()
        return Result.success()
    }
}
